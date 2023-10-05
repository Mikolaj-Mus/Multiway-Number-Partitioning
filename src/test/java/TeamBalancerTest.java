import org.example.Person;
import org.example.Team;
import org.example.TeamBalancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TeamBalancerTest {
    private TeamBalancer teamBalancer;
    private List<Person> individuals;
    private int numTeams;

    @BeforeEach
    public void setUp() {
        teamBalancer = new TeamBalancer();
        individuals = new ArrayList<>();
        individuals.add(new Person("Johnny", 8));
        individuals.add(new Person("Robbie", 5));
        individuals.add(new Person("Juliet", 3));
        individuals.add(new Person("Scarlet", 5));
        individuals.add(new Person("Jude", 9));
        individuals.add(new Person("Deborah", 6));
        numTeams = 3;
    }

    @Test
    public void testFindBestTeamsTest() {
        int numTeams = 3;
        List<Team> bestTeams = teamBalancer.findBestTeams(individuals, numTeams);
        double stdDeviation = teamBalancer.calculateStdDeviation(bestTeams);
        double expectedStdDeviation = 0.41;
        assertEquals(expectedStdDeviation, stdDeviation, 0.01);
    }

    @Test
    public void stdDeviationOverZeroTest() {

        List<Team> bestTeams = teamBalancer.findBestTeams(individuals, numTeams);

        double stdDeviation = teamBalancer.calculateStdDeviation(bestTeams);

        assertTrue(stdDeviation > 0);
    }

    @Test
    public void numOfPeopleInSubnetsMatchMainGroupTest() {

        List<Team> bestTeams = teamBalancer.findBestTeams(individuals, numTeams);
        int numOfPeopleInGroups = 0;
        for (Team team : bestTeams) {
            numOfPeopleInGroups += team.getPeople().size();
        }
        assertEquals(individuals.size(), numOfPeopleInGroups);
    }

    @Test
    public void evenNumberOfPeopleEqualInSubnetsTest() {

        List<Team> bestTeams = teamBalancer.findBestTeams(individuals, numTeams);

        int numOfPeopleInGroups = individuals.size() / numTeams;

        for (Team team : bestTeams) {
            assertEquals(numOfPeopleInGroups, team.getPeople().size());
        }
    }

    @Test
    public void oddNumberOffPeopleEqualInSubnetsTest() {

        numTeams = 4;

        List<Team> bestTeams = teamBalancer.findBestTeams(individuals, numTeams);

        int numOfPeopleInGroups = individuals.size() / numTeams;

        for (Team team : bestTeams) {
            int teamSize = team.getPeople().size();
            assertTrue(teamSize == numOfPeopleInGroups || teamSize == numOfPeopleInGroups + 1);
        }
    }

    @Test
    public void createIndividualTeamsTest() {

        List<Team> teams = teamBalancer.createIndividualTeams(individuals);

        assertEquals(individuals.size(), teams.size());
        for (int i = 0; i < individuals.size(); i++) {
            assertEquals(1, teams.get(i).getPeople().size());
            assertEquals(individuals.get(i), teams.get(i).getPeople().get(0));
        }
    }

    @Test
    public void findOptimalTeamsTest() {

        List<Team> teams = teamBalancer.findOptimalTeams(individuals, numTeams);

        double stdDeviation = teamBalancer.calculateStdDeviation(teams);
        int teamSize = individuals.size() / numTeams;

        assertEquals(numTeams, teams.size());
        assertEquals(0.41, stdDeviation, 0.01);
        for (Team team : teams) {
            assertEquals(teamSize, team.getPeople().size());
        }
    }

    @Test
    public void numberOfTeamsOverUsersTest() {

        int numTeams = 10;

        List<Team> teams = teamBalancer.findBestTeams(individuals, numTeams);

        assertEquals(0, teams.size());
    }

    @Test
    public void calculateStdDeviationTest() {

        individuals.add(new Person("Jimmy", 7));

        List<Team> teams = teamBalancer.findBestTeams(individuals, numTeams);

        double stdDeviation = teamBalancer.calculateStdDeviation(teams);

        assertEquals(0.15, stdDeviation, 0.01);
    }

    @Test
    public void calculateMeanTest() {

        List<Team> teams = teamBalancer.findBestTeams(individuals, numTeams);

        double mean = teamBalancer.calculateMean(teams);

        assertEquals(6.0, mean, 0.01);
    }

    @Test
    public void calculateSumOfSquaredDifferencesTest() {

        List<Team> teams = teamBalancer.findBestTeams(individuals, numTeams);

        double mean = teamBalancer.calculateMean(teams);
        double sumOfSquaredDifferences = teamBalancer.calculateSumOfSquaredDifferences(teams, mean);

        assertEquals(0.5, sumOfSquaredDifferences, 0.01);
    }

    @Test
    public void printTeamsTest() {

        List<Team> teams = teamBalancer.findBestTeams(individuals, numTeams);

        teamBalancer.printTeams(teams);
    }

    @Test
    public void calculateTeamRateTest() {

        Team team = new Team(individuals);

        double teamRate = team.calculateTeamRate();

        assertEquals(6.0, teamRate, 0.01);
    }

    @Test
    public void addPersonTest() {

        Team team = new Team();

        team.addPerson(individuals.get(0));

        assertEquals(1, team.getPeople().size());
        assertEquals(individuals.get(0), team.getPeople().get(0));
    }

    @Test
    public void calculateStdDeviationWithMorePeopleTest() {

        for (int i = 0; i < 100; i++) {
            individuals.add(new Person("User" + i, new Random().nextInt(10) + 1));
        }

        List<Team> teams = teamBalancer.findBestTeams(individuals, numTeams);

        double stdDeviation = teamBalancer.calculateStdDeviation(teams);

        assertEquals(0.01, stdDeviation, 0.01);

    }

    @Test
    public void calculateStdDeviationWithMoreGroupsTest() {

        for (int i = 0; i < 100; i++) {
            individuals.add(new Person("User" + i, new Random().nextInt(10) + 1));
        }
        numTeams = 10;

        List<Team> teams = teamBalancer.findBestTeams(individuals, numTeams);

        double stdDeviation = teamBalancer.calculateStdDeviation(teams);

        assertEquals(0.2, stdDeviation, 0.2);

    }

    @Test
    public void calculateStdDeviationWithMuchMorePeopleTest() {

        for (int i = 0; i < 1000; i++) {
            individuals.add(new Person("User" + i, new Random().nextInt(10) + 1));
        }

        List<Team> teams = teamBalancer.findBestTeams(individuals, numTeams);

        double stdDeviation = teamBalancer.calculateStdDeviation(teams);

        assertEquals(0, stdDeviation, 0.01);

    }

    @Test
    public void calculateStdDeviationWithMuchMoreGroupsTest() {

        for (int i = 0; i < 1000; i++) {
            individuals.add(new Person("User" + i, new Random().nextInt(10) + 1));
        }
        numTeams = 100;

        List<Team> teams = teamBalancer.findBestTeams(individuals, numTeams);

        double stdDeviation = teamBalancer.calculateStdDeviation(teams);

        assertEquals(0.67, stdDeviation, 0.1);

    }
}
