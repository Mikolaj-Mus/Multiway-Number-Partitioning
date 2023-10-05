package org.example;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TeamBalancer {

    public List<Team> findBestTeams(List<Person> individuals, int numTeams) {
        if (numTeams > individuals.size()) {
            return new ArrayList<>();
        }

        if (numTeams == individuals.size()) {
            return createIndividualTeams(individuals);
        }

        return findOptimalTeams(individuals, numTeams);
    }

    public List<Team> createIndividualTeams(List<Person> individuals) {
        return individuals.stream()
                .map(person -> new Team(Collections.singletonList(person)))
                .collect(Collectors.toList());
    }

    public List<Team> findOptimalTeams(List<Person> individuals, int numTeams) {
        List<Team> bestTeams = null;
        double lowestStdDeviation = Double.MAX_VALUE;
        int iterations = 10000;

        for (int i = 0; i < iterations; i++) {
            List<Person> shuffledIndividuals = new ArrayList<>(individuals);
            Collections.shuffle(shuffledIndividuals);
            List<Team> teams = new ArrayList<>();

            for (int j = 0; j < numTeams; j++) {
                teams.add(new Team());
            }

            for (int j = 0; j < shuffledIndividuals.size(); j++) {
                Person person = shuffledIndividuals.get(j);
                Team team = teams.get(j % numTeams);
                team.addPerson(person);
            }

            double stdDeviation = calculateStdDeviation(teams);

            if (stdDeviation < lowestStdDeviation) {
                lowestStdDeviation = stdDeviation;
                bestTeams = new ArrayList<>(teams);
            }
        }

        return bestTeams;
    }

    public double calculateStdDeviation(List<Team> teams) {
        double mean = calculateMean(teams);
        double sumOfSquaredDifferences = calculateSumOfSquaredDifferences(teams, mean);
        return Math.sqrt(sumOfSquaredDifferences / teams.size());
    }

    public double calculateMean(List<Team> teams) {
        double totalRate = 0;

        for (Team team : teams) {
            double teamRate = team.calculateTeamRate();
            totalRate += teamRate;
        }

        return totalRate / teams.size();
    }

    public double calculateSumOfSquaredDifferences(List<Team> teams, double mean) {
        double sumOfSquaredDifferences = 0;

        for (Team team : teams) {
            double teamRate = team.calculateTeamRate();
            sumOfSquaredDifferences += Math.pow(teamRate - mean, 2);
        }

        return sumOfSquaredDifferences;
    }


    public void printTeams(List<Team> teams) {

        DecimalFormat df = new DecimalFormat("#.##");

        for (int i = 0; i < Objects.requireNonNull(teams).size(); i++) {
            Team team = teams.get(i);
            String players = team.getPeople().stream().map(Person::name).collect(Collectors.joining(", "));
            System.out.println("Team no " + (i + 1) + " has " + team.getPeople().size() + " players (" + players + "). Average rate: " + df.format(team.calculateTeamRate()));
        }

        if (teams.isEmpty()) {
            System.out.println("No teams found");
        } else {
            System.out.println("Teams rate standard deviation: " + df.format(calculateStdDeviation(teams)));
        }
    }


}
