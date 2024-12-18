package com.master1.planningpoker.service.Vote;

import com.master1.planningpoker.dtos.request.voteRequests.VoteRequest;
import com.master1.planningpoker.dtos.responses.voteResponses.VoteResponse;
import com.master1.planningpoker.models.Assignment;

import java.util.List;

/**
 * Interface définissant les services de gestion des votes dans l'application.
 * Fournit des méthodes pour soumettre un vote, récupérer des votes, et supprimer des votes.
 */
public interface IVoteService {

    /**
     * Soumet un vote pour un joueur dans un jeu spécifique.
     *
     * @param request La requête contenant les informations du vote à soumettre.
     * @return Un message confirmant la soumission du vote.
     * @throws IllegalArgumentException si les informations du vote sont invalides.
     */
    public String submitVote(VoteRequest request);

    /**
     * Récupère tous les votes de l'application.
     *
     * @return Une liste de réponses de type {@link VoteResponse} représentant tous les votes.
     */
    public List<VoteResponse> getVotes();

    /**
     * Récupère tous les votes associés à un jeu spécifique, identifié par son code.
     *
     * @param code Le code du jeu pour lequel récupérer les votes.
     * @return Une liste de réponses de type {@link VoteResponse} représentant les votes associés au jeu.
     */
    public List<VoteResponse> getVotesByGameCode(String code);

    /**
     * Récupère un vote spécifique en fonction de son identifiant.
     *
     * @param id L'identifiant du vote à récupérer.
     * @return Un objet {@link VoteResponse} représentant le vote demandé.
     * @throws IllegalArgumentException si le vote avec l'identifiant donné n'existe pas.
     */
    public VoteResponse getVote(Long id);

    /**
     * Récupère tous les votes associés à une tâche spécifique, identifiée par son identifiant d'assignation.
     *
     * @param assignmentId L'identifiant de l'assignation pour laquelle récupérer les votes.
     * @return Une liste de réponses de type {@link VoteResponse} représentant les votes associés à l'assignation.
     */
    public List<VoteResponse> getVotesForAssignment(Long assignmentId);

    /**
     * Supprime un vote spécifique pour une tâche donnée, en fonction de l'identifiant de l'assignation.
     *
     * @param assignmentId L'identifiant de l'assignation pour laquelle supprimer le vote.
     * @return Un message confirmant la suppression du vote.
     */
    public String deleteVote(Long assignmentId);

    String evaluateAverage(Assignment assignment);

    String evaluateUnanimity(Assignment assignment);
}
