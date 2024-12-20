package com.master1.planningpoker.service.Vote;

import com.master1.planningpoker.dtos.request.voteRequests.VoteRequest;
import com.master1.planningpoker.dtos.responses.voteResponses.VoteResponse;
import com.master1.planningpoker.mappers.voteMapper.VoteMapper;
import com.master1.planningpoker.models.*;
import com.master1.planningpoker.repositories.AssignmentRepository;
import com.master1.planningpoker.repositories.PlayerRepository;
import com.master1.planningpoker.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des votes pour les assignations dans le jeu de poker.
 * Cette classe gère la soumission, la récupération, et la suppression des votes.
 */
@Service
@RequiredArgsConstructor
public class VoteService implements IVoteService {

    @Autowired
    private final AssignmentRepository assignmentRepository;
    @Autowired
    private final PlayerRepository playerRepository;
    @Autowired
    private final VoteRepository voteRepository;
    @Autowired
    private final VoteMapper voteMapper;




    /**
     * Soumet un vote pour un joueur pour une assignation donnée.
     *
     * @param request La requête contenant les informations du vote, y compris l'ID du joueur, de l'assignation, et la valeur du vote.
     * @return Un message confirmant la soumission du vote.
     * @throws IllegalArgumentException si l'assignation ou le joueur n'existent pas.
     */

    @Override
    public String submitVote(VoteRequest request) {
        // Vérifier si l'assignation et le joueur existent
        Optional<Assignment> assignmentOpt = assignmentRepository.findById(request.getAssignmentId());
        if (assignmentOpt.isEmpty()) {
            throw new IllegalArgumentException("The assignment doesn't exist.");
        }

        Optional<Player> playerOpt = playerRepository.findById(request.getPlayerId());
        if (playerOpt.isEmpty()) {
            throw new IllegalArgumentException("The player doesn't exist.");
        }

        Assignment assignment = assignmentOpt.get();
        Player player = playerOpt.get();

        // Vérifier si le joueur a déjà voté
        boolean playerAlreadyVoted = voteRepository.existsByAssignmentIdAndPlayerId(assignment.getId(), request.getPlayerId());
        if (playerAlreadyVoted) {
            return "Player has already voted.";
        }

        // Enregistrer le vote
        Vote vote = new Vote();
        vote.setAssignment(assignment);
        vote.setPlayer(player);
        vote.setValue(request.getValue());
        voteRepository.save(vote);

        // Vérifier si tous les joueurs ont voté
        if (allPlayersVoted(assignment)) {
            // Appliquer la règle associée
            String ruleResult = applyRule(assignment);

            if (ruleResult.equals("RULE_NOT_RESPECTED")) {
                // Réinitialiser les votes si la règle n'est pas respectée
                voteRepository.deleteAll(voteRepository.findByAssignmentId(assignment.getId()));
                return "The rule is not respected. All players must revote.";
            }

            // Mettre à jour l'assignation avec la difficulté finale
            assignment.setDifficulty(Integer.parseInt(ruleResult));
            assignmentRepository.save(assignment);

            return "The rule is respected. Final value: " + ruleResult;
        }

        return "Waiting for other players to vote.";
    }

    /**
     * Vérifie si tous les joueurs d'un jeu ont voté pour une assignation.
     *
     * @param assignment L'assignation pour laquelle vérifier les votes.
     * @return Vrai si tous les joueurs ont voté, faux sinon.
     */
    private boolean allPlayersVoted(Assignment assignment) {
        // Récupérer la liste des joueurs associés au jeu
        List<Player> players = playerRepository.findByGameId(assignment.getGame().getId());

        // Compter le nombre de votes pour l'assignation
        long totalVotes = voteRepository.countByAssignmentId(assignment.getId());

        // Vérifier si tous les joueurs ont voté
        return players.size() == totalVotes;
    }

    /**
     * Applique la règle du jeu à une assignation.
     * Si la règle n'est pas respectée, retourne "RULE_NOT_RESPECTED".
     *
     * @param assignment L'assignation pour laquelle appliquer la règle.
     * @return La valeur finale si la règle est respectée, ou "RULE_NOT_RESPECTED".
     */
    private String applyRule(Assignment assignment) {
        Game game = assignment.getGame();
        Rule rule = game.getRule();

        // Récupérer les votes associés à l'assignation
        List<Vote> votes = voteRepository.findByAssignmentId(assignment.getId());

        if (rule.getName().equalsIgnoreCase("UNANIMITE")) {
            // Vérifier si tous les votes ont la même valeur
            boolean unanimous = votes.stream()
                    .map(Vote::getValue)
                    .distinct()
                    .count() == 1;
            return unanimous ? String.valueOf(votes.get(0).getValue()) : "RULE_NOT_RESPECTED";
        }

        if (rule.getName().equalsIgnoreCase("MOYEN")) {
            // Calculer la moyenne des votes
            double average = votes.stream()
                    .mapToInt(Vote::getValue)
                    .average()
                    .orElse(0);

            // Exemple : Valider la moyenne selon une logique spécifique (optionnel)
            return (average > 0) ? String.valueOf((int) Math.round(average)) : "RULE_NOT_RESPECTED";
        }

        throw new IllegalArgumentException("Invalid game rule.");
    }

    @Override
    public String evaluateUnanimity(Assignment assignment) {
        // Récupérer les votes pour l'assignation
        List<Vote> votes = voteRepository.findByAssignmentId(assignment.getId());

        // Vérifier si tous les votes ont la même valeur
        boolean unanimous = votes.stream()
                .map(Vote::getValue)
                .distinct()
                .count() == 1;

        if (unanimous) {
            return String.valueOf(votes.get(0).getValue());
        }

        return "RULE_NOT_RESPECTED";
    }
    @Override
    public String evaluateAverage(Assignment assignment) {
        List<Vote> votes = voteRepository.findByAssignmentId(assignment.getId());

        if (votes.isEmpty()) {
            return "0"; // Aucun vote soumis
        }

        // Calculer la moyenne des votes
        double average = votes.stream()
                .mapToInt(Vote::getValue)
                .average()
                .orElse(0);

        // Retourner la moyenne arrondie sous forme de String
        return String.valueOf((int) Math.round(average));
    }
/**
     * Récupère tous les votes enregistrés dans le système.
     *
     * @return Une liste de réponses représentant tous les votes.
     */
    @Override
    public List<VoteResponse> getVotes() {
        return voteRepository.findAll()
                .stream()
                .map(voteMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les votes associés à un jeu spécifique, identifié par son code.
     *
     * @param code Le code du jeu pour lequel récupérer les votes.
     * @return Une liste de réponses représentant les votes associés au jeu.
     */
    @Override
    public List<VoteResponse> getVotesByGameCode(String code) {
        return voteRepository.findVotesByGameCode(code)
                .stream()
                .map(voteMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un vote spécifique en fonction de son identifiant.
     *
     * @param id L'identifiant du vote à récupérer.
     * @return La réponse du vote correspondant à l'identifiant.
     * @throws IllegalArgumentException si le vote avec l'identifiant donné n'existe pas.
     */
    @Override
    public VoteResponse getVote(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vote with id : " + id + " doesn't exist."));
        return voteMapper.toResponse(vote);
    }

    /**
     * Récupère tous les votes associés à une assignation spécifique.
     *
     * @param assignmentId L'identifiant de l'assignation pour laquelle récupérer les votes.
     * @return Une liste de réponses représentant les votes associés à l'assignation.
     */
    @Override
    public List<VoteResponse> getVotesForAssignment(Long assignmentId) {
        List<Vote> votes = voteRepository.findByAssignmentId(assignmentId);
        return votes.stream()
                .map(voteMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Supprime un vote spécifique en fonction de son identifiant.
     *
     * @param id L'identifiant du vote à supprimer.
     * @return Un message confirmant la suppression du vote.
     */
    @Override
    public String deleteVote(Long id) {
        voteRepository.deleteById(id);
        return "Vote : " + id + " deleted successfully";
    }
}
