package com.master1.planningpoker.service.Assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master1.planningpoker.dtos.request.assignmentRequest.AddAssignmentRequest;
import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;
import com.master1.planningpoker.mappers.assignmentMapper.AssignmentMapper;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.repositories.AssignmentRepository;
import com.master1.planningpoker.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @service AssignmentService
 * @brief Service responsable de la gestion des assignments dans le contexte du planning poker.
 *
 * Cette classe fournit des services pour créer, modifier, supprimer et récupérer des assignments, ainsi que pour gérer
 * l'importation et l'exportation de backlogs sous forme de fichiers JSON.
 */
@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssignmentService {

    @Autowired
    private final AssignmentRepository assignmentRepository;

    @Autowired
    private final AssignmentMapper assignmentMapper;

    @Autowired
    private final GameRepository gameRepository;

    /**
     * Ajoute ou modifie un assignment dans la base de données.
     * Si un `id` est fourni, l'assignation existante sera mise à jour, sinon une nouvelle assignation sera créée.
     *
     * @param request La requête contenant les informations pour créer ou modifier un assignment.
     * @return La réponse avec les informations de l'assignation ajoutée ou modifiée.
     * @throws IllegalArgumentException si l'assignation avec le même libelle existe déjà ou si le jeu n'existe pas.
     */
    @Override
    public AssignmentResponse addEditAssignment(AddAssignmentRequest request) {
        boolean exists = assignmentRepository.existsByLibelle(request.getLibelle());
        if (exists) {
            throw new IllegalArgumentException("An assignment with the same libelle already exists.");
        }
        // Vérifie si le jeu existe
        boolean exist = gameRepository.existsById(request.getGameId());
        if (!exist) {
            throw new IllegalArgumentException("This game doesn't exist");
        }

        // Créer et sauvegarder l'assignation
        if (request.getId() == null) {
            Assignment newAssignment = assignmentMapper.toEntity(request);
            Assignment savedAssignment = assignmentRepository.save(newAssignment);
            return assignmentMapper.toResponse(savedAssignment);
        } else {
            Assignment existingAssignment = assignmentRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + request.getId()));

            Game game = gameRepository.findById(request.getGameId())
                    .orElseThrow(() -> new IllegalArgumentException("Game not found with id: " + request.getGameId()));

            // Mise à jour des champs
            existingAssignment.setLibelle(request.getLibelle());
            existingAssignment.setDescription(request.getDescription());
            existingAssignment.setDifficulty(request.getDifficulty());
            existingAssignment.setGame(game);

            // Sauvegarde l'assignation mise à jour
            Assignment updatedAssignment = assignmentRepository.save(existingAssignment);
            return assignmentMapper.toResponse(updatedAssignment);
        }
    }

    /**
     * Récupère tous les assignments d'un jeu spécifique.
     *
     * @param gameId L'identifiant du jeu pour lequel récupérer les assignments.
     * @return Une liste de réponses des assignments associés au jeu spécifié.
     * @throws IllegalArgumentException si le jeu n'existe pas.
     */
    @Override
    public List<AssignmentResponse> getBacklog(Long gameId) {
        // Vérifie si le jeu existe
        boolean gameExists = gameRepository.existsById(gameId);
        if (!gameExists) {
            throw new IllegalArgumentException("Game not found for the given ID: " + gameId);
        }
        return assignmentRepository.findAllByGameId(gameId)
                .stream().map(assignmentMapper::toResponse).collect(Collectors.toList());
    }

    /**
     * Supprime un assignment par son identifiant.
     *
     * @param assignmentId L'identifiant de l'assignation à supprimer.
     * @return Un message confirmant la suppression.
     */
    @Override
    public String removeAssignment(Long assignmentId) {
        assignmentRepository.deleteById(assignmentId);
        return "Assignment with id " + assignmentId + " removed successfully";
    }

    /**
     * Sauvegarde les assignments d'un jeu sous forme de fichier JSON.
     *
     * @param gameId L'identifiant du jeu dont le backlog sera exporté.
     * @return Le chemin du fichier JSON créé.
     * @throws IllegalArgumentException si le jeu n'existe pas.
     * @throws RuntimeException si une erreur se produit lors de la création du fichier.
     */
    @Override
    public String saveBacklogToJson(Long gameId) {
        // Vérifie si le jeu existe
        boolean gameExists = gameRepository.existsById(gameId);
        if (!gameExists) {
            throw new IllegalArgumentException("Game not found for the given ID: " + gameId);
        }

        // Récupère le backlog spécifique du jeu
        List<Assignment> backlog = assignmentRepository.findAllByGameId(gameId);

        List<Map<String, Object>> backlogWithGameId = backlog.stream().map(assignment -> {
            Map<String, Object> assignmentMap = new HashMap<>();
            assignmentMap.put("id", assignment.getId());
            assignmentMap.put("libelle", assignment.getLibelle());
            assignmentMap.put("description", assignment.getDescription());
            assignmentMap.put("difficulty", assignment.getDifficulty());
            assignmentMap.put("gameId", gameId);
            // Ajouter les votes
            assignmentMap.put("votes", assignment.getVotes().stream().map(vote -> {
                Map<String, Object> voteMap = new HashMap<>();
                voteMap.put("id", vote.getId());
                voteMap.put("value", vote.getValue());
                voteMap.put("player", Map.of(
                        "id", vote.getPlayer().getId(),
                        "pseudo", vote.getPlayer().getPseudo(),
                        "admin", vote.getPlayer().isAdmin()
                ));
                return voteMap;
            }).collect(Collectors.toList()));
            return assignmentMap;
        }).collect(Collectors.toList());

        // Crée et sauvegarde le backlog dans un fichier JSON
        Path downloadDir = Paths.get(System.getProperty("user.home"), "Downloads", "backlog");
        File backlogDir = downloadDir.toFile();
        if (!backlogDir.exists() && !backlogDir.mkdirs()) {
            throw new RuntimeException("Failed to create backlog directory: " + backlogDir.getAbsolutePath());
        }

        String fileName = "backlog_" + gameId + ".json";
        File filePath = new File(backlogDir, fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(filePath, backlogWithGameId);
            return filePath.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write backlog to file: " + e.getMessage(), e);
        }
    }


    /**
     * Récupère un assignment par son identifiant.
     *
     * @param id L'identifiant de l'assignation à récupérer.
     * @return La réponse de l'assignation correspondante.
     * @throws IllegalArgumentException si l'assignation n'est pas trouvée.
     */
    @Override
    public AssignmentResponse getAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Assignment with id: " + id + " not found"));
        return assignmentMapper.toResponse(assignment);
    }

    /**
     * Récupère tous les assignments.
     *
     * @return Une liste de toutes les réponses des assignments.
     */
    @Override
    public List<AssignmentResponse> getAssignments() {
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream()
                .map(assignmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère tous les assignments associés à un jeu spécifié par son code.
     *
     * @param gameCode Le code du jeu pour lequel récupérer les assignments.
     * @return Une liste des assignments associés au jeu spécifié par son code.
     */
    @Override
    public List<AssignmentResponse> getAssignmentsByGameCode(String gameCode) {
        List<Assignment> assignments = assignmentRepository.findAssignmentsByGameCode(gameCode);
        return assignments.stream()
                .map(assignmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Charge un backlog d'assignments à partir d'un fichier JSON et les ajoute à un jeu.
     *
     * @param gameId  L'identifiant du jeu auquel les assignments doivent être ajoutés.
     * @param filePath Le chemin du fichier JSON contenant les assignments.
     * @throws RuntimeException si une erreur survient lors du chargement du fichier.
     */
    @Override
    public void loadBacklogFromJson(Long gameId, String filePath) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found for the given ID: " + gameId));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Assignment> assignments = List.of(objectMapper.readValue(new File(filePath), Assignment[].class));

            for (Assignment assignment : assignments) {
                assignment.setGame(game);
                assignmentRepository.save(assignment);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load backlog from file: " + e.getMessage());
        }
    }
}
