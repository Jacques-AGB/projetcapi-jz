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
        Optional<Assignment> assignment = assignmentRepository.findById(request.getAssignmentId());
        if (assignment.isEmpty()) {
            throw new IllegalArgumentException("An assignment doesn't exist.");
        }

        Optional<Player> playerExists = playerRepository.findById(request.getPlayerId());
        if (playerExists.isEmpty()) {
            throw new IllegalArgumentException("A player doesn't exist.");
        }

        // Vérifier si le joueur a déjà voté pour cette assignation
        boolean playerAlreadyVoted = voteRepository.existsByAssignmentIdAndPlayerId(assignment.get().getId(), request.getPlayerId());
        if (playerAlreadyVoted) {
            return "Player has already voted.";
        }

        // Récupérer le nombre de joueurs et de votes pour cette assignation
        List<Player> players = playerRepository.findByGameId(assignment.get().getGame().getId());
        long totalVotes = voteRepository.countByAssignmentId(assignment.get().getId());

        // Vérifier si le nombre de votes dépasse le nombre de joueurs
        if (totalVotes >= players.size()) {
            return "All players have already voted.";
        }

        // Créer un nouveau vote
        Vote vote = new Vote();
        vote.setAssignment(assignment.get());
        vote.setPlayer(playerExists.get());
        vote.setValue(request.getValue());

        // Sauvegarder le vote
        voteRepository.save(vote);

        // Vérifier si tous les joueurs ont voté
        if (allPlayersVoted(assignment.get())) {
            // Récupérer la règle du jeu associée à l'assignation
            Game game = assignment.get().getGame();
            Rule rule = game.getRule();

            // Appliquer la règle de vote selon la règle définie dans le jeu
            if (rule.getName().equals("UNANIMITE")) {
                // Appliquer l'unanimité et récupérer la valeur ou "0"
                String voteValue = evaluateUnanimity(assignment.get());
                if (!voteValue.equals("0")) {
                    // Si l'unanimité est atteinte, mettre à jour la difficulté
                    assignment.get().setDifficulty(Integer.parseInt(voteValue));
                    assignmentRepository.save(assignment.get());
                }
                return voteValue;
            } else if (rule.getName().equals("MOYEN")) {
                // Appliquer la moyenne et récupérer la valeur
                String averageValue = evaluateAverage(assignment.get());
                if (!averageValue.equals("0")) {
                    // Si une moyenne est calculée, mettre à jour la difficulté
                    assignment.get().setDifficulty(Integer.parseInt(averageValue));
                    assignmentRepository.save(assignment.get());
                }
                return averageValue;
            } else {
                throw new IllegalArgumentException("Invalid game rule.");
            }
        } else {
            return "Waiting for other players to vote.";
        }
    }


    public boolean allPlayersVoted(Assignment assignment) {
        List<Player> players = playerRepository.findByGameId(assignment.getGame().getId());
        long totalVotes = voteRepository.countByAssignmentId(assignment.getId());

        // Vérifie si tous les joueurs ont voté
        return players.size() == totalVotes;
    }

    /**
     * Vérifie si tous les votes sont unanimes pour une assignation.
     *
     * @param assignment L'assignation à évaluer.
     * @return Un message indiquant si le vote est unanime ou non.
     */
    @Override
    public String evaluateUnanimity(Assignment assignment) {
        List<Vote> votes = voteRepository.findByAssignmentId(assignment.getId());

        // Vérifie si tous les votes sont identiques
        boolean isUnanimous = votes.stream().allMatch(vote -> vote.getValue() == votes.getFirst().getValue());

        if (isUnanimous) {
            // Si l'unanimité est atteinte, retourne la valeur du vote en String
            return String.valueOf(votes.getFirst().getValue());
        } else {
            // Sinon, retourne "0"
            return "0";
        }
    }

    /**
     * Calcule la moyenne des votes pour une assignation.
     *
     * @param assignment L'assignation à évaluer.
     * @return Un message avec la moyenne des votes.
     */
    @Override
    public String evaluateAverage(Assignment assignment) {
        List<Vote> votes = voteRepository.findByAssignmentId(assignment.getId());

        if (votes.isEmpty()) {
            return "No votes submitted yet.";
        }

        // Calculer la moyenne des votes
        double average = votes.stream()
                .mapToInt(Vote::getValue)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Cannot calculate average, no votes available"));

        return "The average vote value is: " + average;
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
