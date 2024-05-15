import nl.uu.cs.is.apapl.apapl.Environment;
import nl.uu.cs.is.apapl.apapl.ExternalActionFailedException;
import nl.uu.cs.is.apapl.apapl.data.APLFunction;
import nl.uu.cs.is.apapl.apapl.data.APLIdent;
import nl.uu.cs.is.apapl.apapl.data.APLNum;
import nl.uu.cs.is.apapl.apapl.data.Term;

/**
 * === About this file
 * This is an example of a very simple environment that communicates with a single 2APl agent.
 * As you can see, this class extends the Environment class. This will give us several
 * methods that we can use to communicate with the agents, and we can create methods that the
 * agents can use to perform external actions.
 *
 * Below you will find all the basic functionality that an environment offers explained.
 *
 * === How to get this example running
 * First of all, I strongly recommend to use Eclipse as your editor since it has been used to
 * develop 2APL as well. The use of the Eclipse plugin that comes with the full package of 2APL
 * is not recommended, since it is not completely bug-free. In what follows I will assume you are
 * wise and have chosen for Eclipse.
 *
 * 1. Create a new java project in Eclipse and add this file.
 * 2. Add 2apl.jar to the build path by adding it as an external jar.
 * 3. Before this environment can be used, it needs to be compiled into a JAR-file first.
 *    In the folder of this example you will also find a pre-compiled version of this JAR
 *    already (env.jar), but it is fairly easy to do it yourself. You need to create a runnable jar
 *    from this project, with this class (Env.java) as its main class. Therefore this class is REQUIRED
 *    to have a main method.
 * 4. Once you have created this JAR, you can refer to it in the .mas file that specifies the
 *    components of the multiagent system. The mas file of this example is called config.mas. This file
 *    defines what environment to use, and what agents will exist. Agents are specified as .2apl files.
 *    In our example we have one agent that is built from agent.2apl.
 * 5. Put all the files (the JAR, config.mas and agent.2apl) in one directory and run 2APL. You can
 *    run 2APL simply by running 2apl.jar. If you want debug information as well, you need to run
 *    this jar from Eclipse using APAPL as its main class.
 * 6. Open the .mas file, press 'play' and the example should be working.
 *
 * @author Marc van Zee (marcvanzee@gmail.com), Utrecht University
 *
 */
public class Env extends Environment {
    private final boolean log = true;

    // list of agents (Agent)
	protected ObsVect 						_agents = new ObsVect( this );
	

    /**
     * We do not use this method, but we need it so that the JAR file that we will create can point
     * to this class as the main class. This is only possible if the class contains  main method.
     * @param args arguments
     */
    public static void main(String [] args) {
        //todo interface with user to request product
        String[] products = {"Product1", "Product2", "Product3", "Product4"};

        // Prompt the user to select a product
        System.out.println("Please select a product:");

        // Display product options
        for (int i = 0; i < products.length; i++) {
            System.out.println((i+1) + ". " + products[i]);
        }

        // Read user input
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        // Validate user input
        if (choice >= 1 && choice <= products.length) {
            String selectedProduct = products[choice - 1];
            System.out.println("You have selected: " + selectedProduct);
            // Call your method to handle the selected product (e.g., chooseProduct(selectedProduct))
            Env env = new Env();
            env.chooseProduct(selectedProduct);
        } else {
            System.out.println("Invalid selection. Please select a number between 1 and " + products.length);
        }
        
        scanner.close();
    }

    /**
     * This method is automatically called whenever an agent enters the MAS.
     * @param agName the name of the agent that just registered
     */
    protected void addAgent(String agName) {
        log("env> agent " + agName + " has registered to the environment.");

        /* If we want to send information to a 2APL agent, we need to code this into special
         * objects. We can then send these objects to the agent so that he can parse them correctly.
         * All the objects extend the basic class "Term".
         *
         * We distinguish between the following objects:

         * APLNum			This is equal to int and is for example instantiated by: new APLNum(0)
         * APLIdent			Equal to String, instantiated by: new APLIdent("string")
         * APLList			Can be seen as a LinkedList and will be parsed as a Prolog list in 2APL
         *					See the constructor comments of this class for information on how to use it
         * APLFunction		Represents a function, where the arguments of the function again need to be
         *					Term objects. For example, the function: func(0) should be instantiated as
         *					new APLFunction("func", new APLNum(0))
         */
        APLIdent aplagName = new APLIdent(agName);
        APLFunction event = new APLFunction("name", aplagName);

        // If we throw an event, we always need to throw an APLFunction.
        throwEvent(event, agName);

        // note: we can also throw an event to all agents by letting out the last parameter:
        // throwEvent(event);
    }

    private void chooseProduct(String productID) {
        log("env> Product " + productID + " has been chosen.");

        // Constructing the event term for choosing a product
        APLIdent productIDTerm = new APLIdent(productID);
        APLFunction event = new APLFunction("request", productIDTerm);

 
        throwEvent(event, "tradeManager");
    }

    private void log(String str) {
        if (log) System.out.println(str);
    }

    /* Standard functions --------------------------------------*/
	
	private void notifyEvent(String parm1, Point ptPosition)
	{
		APLNum	nX	= new APLNum((double)(ptPosition.getX()));
		APLNum	nY	= new APLNum((double)(ptPosition.getY()));

		// Send an external event to all agents within the senserange.
		ArrayList<String> targetAgents = new ArrayList<String>();
		for (Agent a : agentmap.values())
		{
			// Changed SA: I got no idea why there is always 1 agent which does not exists, 
			// but this fixes the exceptions
			if ((a.getPosition() != null) && (ptPosition.distance(a.getPosition()) <= getSenseRange()))
				targetAgents.add(a.getName());
		}

		writeToLog("EVENT: "+parm1+"("+nX+","+nY+")"+" to "+targetAgents);

		if (!targetAgents.isEmpty())
		{
			notifyAgents(new APLFunction(parm1,nX,nY),targetAgents.toArray(new String[0]));
		}
	}
}


