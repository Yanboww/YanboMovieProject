import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm))
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortString(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String castName= listToSort.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && castName.compareTo(listToSort.get(possibleIndex-1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, castName);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void displayCastInfo(String name)
    {
        ArrayList<Movie> part = new ArrayList<Movie>();
        name = name.toLowerCase();
       for(Movie film : movies)
       {
           if(film.getCast().toLowerCase().contains(name)) part.add(film);
       }
       sortResults(part);
       for(int i =0;i<part.size();i++)
       {
           int order = i+1;
           System.out.println(order+"."+" "+part.get(i).getTitle());
       }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = part.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    public void displayGenreInfo(String genre)
    {
        ArrayList<Movie> part = new ArrayList<Movie>();
        genre = genre.toLowerCase();
        for(Movie film : movies)
        {
            if(film.getGenres().toLowerCase().contains(genre)) part.add(film);
        }
        sortResults(part);
        for(int i =0;i<part.size();i++)
        {
            int order = i+1;
            System.out.println(order+"."+" "+part.get(i).getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = part.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void searchCast()
    {
        System.out.print("Enter a cast member: ");
        String cast = scanner.nextLine();
        cast = cast.toLowerCase();
        ArrayList<String> names = new ArrayList<String>();
        for(Movie file: movies)
        {
            String actors = file.getCast();
            String [] actorArray = actors.split("\\|");
            for(String actor : actorArray)
            {
                String actorName = actor.toLowerCase();
                if(actorName.contains(cast)) {
                    if(!names.contains(actor)) names.add(actor);
                }
            }
        }
        sortString(names);
        for(int i =0;i<names.size();i++)
        {
            int order = i+1;
            System.out.println(order+"."+" " +names.get(i));
        }

        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        String selectedMember = names.get(choice-1);
       displayCastInfo(selectedMember);

    }

    private void searchKeywords()
    {
        System.out.print("Please enter a keyword: ");
        String keyTerm = scanner.nextLine();
        keyTerm = keyTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(Movie film : movies)
        {
            if(film.getKeywords().toLowerCase().contains(keyTerm)) results.add(film);
        }
        sortResults(results);
        for(int i =0;i<results.size();i++)
        {
            int order = i+1;
            String title = results.get(i).getTitle();
            System.out.println(order+"."  + " " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listGenres()
    {
        ArrayList<String> genres = new ArrayList<String>();
        for(Movie film: movies)
        {
            String[] movieGenres = film.getGenres().split("\\|");
            for(String genreType: movieGenres)
            {
                if(!genres.contains(genreType)) genres.add(genreType);
            }
        }
        sortString(genres);
        for(int i =0;i<genres.size();i++)
        {
            int order = i+1;
            System.out.println(order+"."+" " + genres.get(i));
        }
        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        String selectedMember = genres.get(choice-1);
        displayGenreInfo(selectedMember);


    }

    private void listHighestRated()
    {
        Double[] topRated = new Double[movies.size()];
        for(int i = 0;i<topRated.length;i++)
        {
            topRated[i] =movies.get(i).getUserRating();
        }
        Arrays.sort(topRated, Collections.reverseOrder());
        Movie[] topRatedNames = new Movie[50];
        int count = 0;
        for(int i = 0;i<50;i++)
        {
            double rating = topRated[i];
            for(Movie film: movies)
            {
                if(film.getUserRating() == rating) {
                    if (!Arrays.asList(topRatedNames).contains(film))
                    {
                        topRatedNames[count] = film;
                        if(count<49)
                        {
                            count++;
                        }
                    }
                }
            }

        }
        for(int i =0;i<topRatedNames.length;i++)
        {
            int order = i+1;
            System.out.println(order+"."+" " + topRatedNames[i].getTitle() + ": " + topRatedNames[i].getUserRating());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = topRatedNames[choice - 1];

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listHighestRevenue()
    {
        Integer[] topIncome = new Integer[movies.size()];
        for(int i = 0;i<topIncome.length;i++)
        {
            topIncome[i] =movies.get(i).getRevenue();
        }
        Arrays.sort(topIncome, Collections.reverseOrder());
        Movie[] topIncomeNames = new Movie[50];
        int count = 0;
        for(int i = 0;i<50;i++)
        {
            int income = topIncome[i];
            for(Movie film: movies)
            {
                if(film.getRevenue() == income) {
                    if (!Arrays.asList(topIncomeNames).contains(film))
                    {
                        topIncomeNames[count] = film;
                        if(count<49)
                        {
                            count++;
                        }
                    }
                }
            }

        }
        for(int i =0;i<topIncomeNames.length;i++)
        {
            int order = i+1;
            System.out.println(order+"."+" " + topIncomeNames[i].getTitle() + ": " + topIncomeNames[i].getRevenue());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = topIncomeNames[choice - 1];

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
