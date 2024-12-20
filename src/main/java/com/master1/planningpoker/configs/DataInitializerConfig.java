package com.master1.planningpoker.configs;


import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.service.Player.IPlayerService;
import com.master1.planningpoker.service.Rule.IRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializerConfig {

    @Autowired
    private IRuleService ruleService;

    @Autowired
    private IPlayerService playerService;


    
    /**
     * Méthode qui s'exécute automatiquement au démarrage de l'application.
     * Elle permet d'insérer des données initiales (rules et player)
     */
    @Bean
    CommandLineRunner initData() {
        return args -> {
            // Initialisation des Rules
            initializeDefaultRules();
            // Initialisation du joueur Admin
            initializeAdminPlayer();
        };
    }
    private void initializeDefaultRules() {
        String[] ruleNames = {"UNANIMITE", "MOYEN"};

        for (String ruleName : ruleNames) {
            try {
                System.out.println("Adding rule: " + ruleName);
                ruleService.addEditRule(new AddRuleRequest(null, ruleName));
            } catch (Exception e) {
                System.out.println("Rule '" + ruleName + "' already exists or error: " + e.getMessage());
            }
        }
    }

    private void initializeAdminPlayer() {
        try {
            System.out.println("Adding default admin player...");
            CreatePlayerRequest playerRequest = new CreatePlayerRequest();
            playerRequest.setId(null);
            playerRequest.setPseudo("admin");
            playerRequest.setAdmin(true);
            playerRequest.setCode(null);

            playerService.createEditPlayer(playerRequest);
        } catch (Exception e) {
            System.out.println("Admin player already exists or error: " + e.getMessage());
        }
    }
}
