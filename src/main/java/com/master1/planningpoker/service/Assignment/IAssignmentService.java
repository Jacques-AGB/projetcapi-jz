package com.master1.planningpoker.service.Assignment;

import com.master1.planningpoker.dtos.request.assignmentRequest.AddAssignmentRequest;
import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;
import com.master1.planningpoker.models.Assignment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface qui définit les services relatifs à la gestion des assignments dans un jeu de planification poker.
 * Cette interface inclut des méthodes pour créer, modifier, supprimer, récupérer, exporter et importer des assignments.
 */
public interface IAssignmentService {

    /**
     * Ajoute ou modifie un assignment dans le système.
     * Si l'assignation existe déjà, elle sera mise à jour, sinon une nouvelle assignation sera créée.
     *
     * @param request La requête contenant les informations pour ajouter ou modifier un assignment.
     * @return La réponse contenant les informations sur l'assignation créée ou mise à jour.
     * @throws IllegalArgumentException si l'assignation avec le même libelle existe déjà ou si le jeu n'existe pas.
     */
    AssignmentResponse addEditAssignment(AddAssignmentRequest request);

    /**
     * Supprime un assignment existant par son identifiant.
     *
     * @param assignmentId L'identifiant de l'assignation à supprimer.
     * @return Un message confirmant la suppression de l'assignation.
     */
    String removeAssignment(Long assignmentId);

    /**
     * Sauvegarde le backlog (ensemble des assignments) d'un jeu spécifié sous forme de fichier JSON.
     *
     * @param gameId L'identifiant du jeu dont le backlog sera exporté.
     * @return Le chemin absolu du fichier JSON sauvegardé.
     * @throws IllegalArgumentException si le jeu n'existe pas pour l'ID donné.
     */
    String saveBacklogToJson(Long gameId);

    /**
     * Récupère un assignment spécifique par son identifiant.
     *
     * @param id L'identifiant de l'assignation à récupérer.
     * @return La réponse contenant les informations sur l'assignation.
     * @throws IllegalArgumentException si l'assignation n'est pas trouvée.
     */
    AssignmentResponse getAssignment(Long id);

    /**
     * Récupère tous les assignments disponibles dans le système.
     *
     * @return Une liste des réponses contenant les informations sur tous les assignments.
     */
    List<AssignmentResponse> getAssignments();

    /**
     * Récupère tous les assignments associés à un jeu spécifié par son code.
     *
     * @param gameCode Le code du jeu dont les assignments doivent être récupérés.
     * @return Une liste des réponses contenant les informations sur les assignments associés au jeu spécifié.
     */
    List<AssignmentResponse> getAssignmentsByGameCode(String gameCode);

    /**
     * Récupère tous les assignments associés à un jeu spécifique par son identifiant.
     *
     * @param gameId L'identifiant du jeu pour lequel récupérer les assignments.
     * @return Une liste de réponses contenant les informations sur les assignments du jeu.
     * @throws IllegalArgumentException si le jeu n'existe pas pour l'ID donné.
     */
    List<AssignmentResponse> getBacklog(Long gameId);

    /**
     * Charge un backlog d'assignments à partir d'un fichier JSON et les ajoute au jeu spécifié.
     *
     * @param gameId  L'identifiant du jeu auquel les assignments doivent être ajoutés.
     * @param filePath Le chemin du fichier JSON contenant les assignments.
     * @throws RuntimeException si une erreur survient lors de la lecture du fichier ou de l'ajout des assignments.
     */
    void loadBacklogFromJson(Long gameId, MultipartFile filePath);
}
