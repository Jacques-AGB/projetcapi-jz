package com.master1.planningpoker.service.Assignment;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.web.multipart.MultipartFile;
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


    public String saveBacklogToJson(Long gameId) {
        // Vérifie si le jeu existe
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game with id: " + gameId + " not found"));

        // Récupère les assignments associés au jeu
        List<Assignment> assignments = assignmentRepository.findAllByGameId(gameId);

        // Vérifie si le backlog est vide
        if (assignments.isEmpty()) {
            throw new IllegalArgumentException("Le backlog du jeu est vide.");
        }

        // Crée le répertoire pour sauvegarder le fichier JSON (si nécessaire)
        Path downloadDir = Paths.get(System.getProperty("user.home"), "Downloads", "backlog");
        File backlogDir = downloadDir.toFile();
        if (!backlogDir.exists() && !backlogDir.mkdirs()) {
            throw new RuntimeException("Impossible de créer le répertoire pour sauvegarder le backlog.");
        }

        // Crée le nom du fichier en fonction de l'ID du jeu
        String fileName = "backlog_" + gameId + ".json";
        File filePath = new File(backlogDir, fileName);

        // Utilise ObjectMapper pour convertir les assignments en JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convertir les assignments en liste d'AssignmentResponse
            List<AssignmentResponse> assignmentResponses = assignments.stream()
                    .map(assignmentMapper::toResponse)
                    .collect(Collectors.toList());

            // Sauvegarde la liste d'assignments dans un fichier JSON
            objectMapper.writeValue(filePath, assignmentResponses);

            System.out.println("Backlog du jeu avec id " + gameId + " sauvegardé sous le fichier : " + filePath.getAbsolutePath());
            return filePath.getAbsolutePath();

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du backlog en fichier JSON.", e);
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



    public void loadBacklogFromJson(Long gameId, MultipartFile file) {
        // Vérifie si le jeu existe
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game with id: " + gameId + " not found"));

        try {
            // Lire le fichier JSON directement à partir du MultipartFile
            ObjectMapper objectMapper = new ObjectMapper();
            List<Assignment> assignments = objectMapper.readValue(file.getInputStream(), new TypeReference<List<Assignment>>() {});

            // Assurez-vous que le backlog n'est pas vide
            if (assignments.isEmpty()) {
                throw new IllegalArgumentException("Le fichier backlog est vide.");
            }

            // Ajoutez chaque assignment au jeu
            for (Assignment assignment : assignments) {
                assignment.setGame(game);  // Associez l'assignation au jeu
                assignmentRepository.save(assignment);  // Sauvegardez l'assignation dans la base de données
            }

            System.out.println("Backlog chargé avec succès pour le jeu avec id : " + gameId);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier backlog JSON", e);
        }
    }
}
