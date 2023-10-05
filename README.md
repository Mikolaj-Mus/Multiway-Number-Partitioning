# Team Balancer Algorithm

## Description
The problem of dividing numbers into groups is an NP-hard 
problem, which means that there is no polynomial-time 
algorithm for solving it. For my algorithm, I have chosen a 
method of randomly assigning numbers to lists under certain 
conditions while keeping track of the best result. I have 
determined that specifying 10,000 iterations with different 
combinations of lists is optimal, and I consider this to be 
a suitable solution for cases involving a small number of 
individuals and groups. However, for larger numbers, the 
program's execution time increases because the computational 
complexity of my algorithm depends on O(individuals * groups * 
iterations). If necessary, the number of iterations can be 
adjusted to achieve results in a shorter time or with greater
precision, depending on specific requirements.

## Code Overview
### 'TeamBalancer' Class
* **'findBestTeams'**: Main method to find the best-balanced teams.
* **'createIndividualTeams'**: Creates individual teams for each person.
* **'findOptimalTeams'**: Finds optimal teams with minimized standard deviation.
* **'calculateStdDeviation'**: Calculates the standard deviation of team ratings.
* **'calculateMean'**: Calculates the mean (average) rating of all teams.
* **'calculateSumOfSquaredDifferences'**: Calculates the sum of squared differences for standard deviation calculation.
* **'printTeams'**: Prints the information about teams and their ratings.
### 'Person' Class
* Represents an individual with a name and a rating.
### 'Team' Class
* Represents a team of individuals with a list of people and methods to calculate the team's rating.
### 'TeamBalancerTest' Class
* Contains JUnit tests to validate the functionality of the **'TeamBalancer'** class.

## How to Use
1. Clone the repository containing the code.
2. Import the code into your Java IDE.
3. Run the **'TeamBalancerTest'** class to execute the test cases and ensure the algorithm's correctness.

## Test Cases
The test cases in **'TeamBalancerTest'** validate various 
aspects of the algorithm, such as team balance, standard 
deviation calculation, and handling different input scenarios.

## Sample Usage
Here's an example of how to use the **'TeamBalancer'** class:

    public static void main(String[] args) {
        TeamBalancer teamBalancer = new TeamBalancer();
        List<Person> individuals = new ArrayList<>();
    
        // Add individuals with names and ratings
        individuals.add(new Person("Johnny", 8));
        individuals.add(new Person("Robbie", 5));
        // ... Add more individuals
        
        int numTeams = 3; // Define the number of teams
        
        List<Team> bestTeams = teamBalancer.findBestTeams(individuals, numTeams);
        
        // Print the best-balanced teams
        teamBalancer.printTeams(bestTeams);
    }