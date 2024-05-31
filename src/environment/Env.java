import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import apapl.Environment;
import apapl.ExternalActionFailedException;
import apapl.data.APLFunction;
import apapl.data.APLIdent;
import apapl.data.APLNum;
import apapl.data.Term;
import blockworld.lib.ObsVect;
import blockworld.lib.ObsVectListener;
import com.opencsv.*;

/**
 * === About this file
 * This is an example of a very simple environment that communicates with a
 * single 2APl agent.
 * As you can see, this class extends the Environment class. This will give us
 * several
 * methods that we can use to communicate with the agents, and we can create
 * methods that the
 * agents can use to perform external actions.
 *
 * Below you will find all the basic functionality that an environment offers
 * explained.
 *
 * === How to get this example running
 * First of all, I strongly recommend to use Eclipse as your editor since it has
 * been used to
 * develop 2APL as well. The use of the Eclipse plugin that comes with the full
 * package of 2APL
 * is not recommended, since it is not completely bug-free. In what follows I
 * will assume you are
 * wise and have chosen for Eclipse.
 *
 * 1. Create a new java project in Eclipse and add this file.
 * 2. Add 2apl.jar to the build path by adding it as an external jar.
 * 3. Before this environment can be used, it needs to be compiled into a
 * JAR-file first.
 * In the folder of this example you will also find a pre-compiled version of
 * this JAR
 * already (env.jar), but it is fairly easy to do it yourself. You need to
 * create a runnable jar
 * from this project, with this class (Env.java) as its main class. Therefore
 * this class is REQUIRED
 * to have a main method.
 * 4. Once you have created this JAR, you can refer to it in the .mas file that
 * specifies the
 * components of the multiagent system. The mas file of this example is called
 * config.mas. This file
 * defines what environment to use, and what agents will exist. Agents are
 * specified as .2apl files.
 * In our example we have one agent that is built from agent.2apl.
 * 5. Put all the files (the JAR, config.mas and agent.2apl) in one directory
 * and run 2APL. You can
 * run 2APL simply by running 2apl.jar. If you want debug information as well,
 * you need to run
 * this jar from Eclipse using APAPL as its main class.
 * 6. Open the .mas file, press 'play' and the example should be working.
 *
 * @author Marc van Zee (marcvanzee@gmail.com), Utrecht University
 *
 */
public class Env extends Environment implements ObsVectListener {
    private final boolean log = true;
    // private HashMap<String,Agent> agentmap = new HashMap<String,Agent>();

    // resourceStock
    private HashMap<Integer, Integer> resourceStock = new HashMap<Integer, Integer>();

    // processedResources
    private HashMap<Integer, Integer> processedResources = new HashMap<Integer, Integer>();
    // manufaturedComponents
    private HashMap<Integer, Integer> manufacturedComponents = new HashMap<Integer, Integer>();
    // aasembledProducts
    private HashMap<Integer, Integer> assembledProducts = new HashMap<Integer, Integer>();

    /* ---- ALL the stuff in the environment ----- */

    // list of agents (Agent)
    protected ObsVect _agents = new ObsVect(this);

    private Map<Integer, Integer> productQuantities;

    /**
     * We do not use this method, but we need it so that the JAR file that we will
     * create can point
     * to this class as the main class. This is only possible if the class contains
     * main method.
     * 
     * @param args arguments
     */
    public static void main(String[] args) {
    }

    /**
     * This method is automatically called whenever an agent enters the MAS.
     * 
     * @param agName the name of the agent that just registered
     */
    protected void addAgent(String agName) {
        log("env> agent " + agName + " has registered to the environment.");

        /*
         * If we want to send information to a 2APL agent, we need to code this into
         * special
         * objects. We can then send these objects to the agent so that he can parse
         * them correctly.
         * All the objects extend the basic class "Term".
         *
         * We distinguish between the following objects:
         * 
         * APLNum This is equal to int and is for example instantiated by: new APLNum(0)
         * APLIdent Equal to String, instantiated by: new APLIdent("string")
         * APLList Can be seen as a LinkedList and will be parsed as a Prolog list in
         * 2APL
         * See the constructor comments of this class for information on how to use it
         * APLFunction Represents a function, where the arguments of the function again
         * need to be
         * Term objects. For example, the function: func(0) should be instantiated as
         * new APLFunction("func", new APLNum(0))
         */

        // for testing purpose
        // APLIdent aplagName = new APLIdent(agName);
        // APLFunction event = new APLFunction("name", aplagName);

        // If we throw an event, we always need to throw an APLFunction.
        // throwEvent(event, agName);

        // note: we can also throw an event to all agents by letting out the last
        // parameter:
        // throwEvent(event);

        
        if (agName.equals("tradeManager")) {
            request(agName);
        }

        if (agName.equals("resourceExtractorManager")) {
            // APLIdent planet = new APLIdent("MARS");
            // APLFunction event = new APLFunction("deployExplorationDrones", planet);
            // throwEvent(event, agName);

            // Initialize the resource stock
            resourceStock.put(1, 0);
            resourceStock.put(2, 0);
            resourceStock.put(3, 0);
            resourceStock.put(4, 0);

            // APLIdent resourceID = new APLIdent("1");
            // // APLIdent region = new APLIdent("1");
            // APLFunction event_2 = new APLFunction("addPriority", resourceID);
            // throwEvent(event_2, agName);
        }
    }

    private void request(String agName){
        // todo interface with user to request product
        //String[] products = { "1", "2", "3", "4" };
        String[] products = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
            "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
            "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
            "81", "82", "83", "84", "85", "86", "87", "88", "89", "90",
            "91", "92", "93", "94", "95", "96", "97", "98", "99"
        };

        // Prompt the user to select a product
        log("Please select a product:");

        // Display product options
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + "Product " + products[i]);
        }
        java.util.Random random = new java.util.Random();
        int random_choice = random.nextInt(products.length);
        String choice = products[random_choice];
        int choiceInt = Integer.parseInt(choice);
        // Validate user input
        if (choiceInt >= 1 && choiceInt <= products.length) {
            String selectedProduct = products[choiceInt - 1];
            System.out.println("You have selected: " + selectedProduct);
            // Call your method to handle the selected product (e.g.,
            // chooseProduct(selectedProduct))
            startNegotiation(choiceInt);
            chooseProduct(selectedProduct, agName);
        } else {
            System.out.println("Invalid selection. Please select a number between 1 and " + products.length);
        }

     
    }

    private void chooseProduct(String productID, String agName) {
        log("env> Product " + productID + " has been chosen.");

        // Constructing the event term for choosing a product
        APLIdent productIDTerm = new APLIdent(productID);
        APLFunction event = new APLFunction("request", productIDTerm);
        throwEvent(event, agName);
    }

    private void log(String str) {
        if (log)
            System.out.println(str);
    }

    @Override
    public void onAdd(int arg0, Object arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onAdd'");
    }

    @Override
    public void onRemove(int arg0, Object arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onRemove'");
    }

    /*
     * Standard functions --------------------------------------
     * External actions of agents can be caught by defining methods that have a Term
     * as return value.
     * 
     * @param agName The name of the agent that does the external action
     * 
     * @param aplNum The product id
     */
    public Term trackInventory(String agName, APLNum aplNum) throws ExternalActionFailedException {
        String num = aplNum.toString();
        int threshold = 5;
        boolean overTh = false;
        log("env> agent " + agName + " wants to track inventory for product with id " + num);
        // TODO check inventory and give a response
        String path = "src\\Inventory.csv";
        List<String[]> csvBody = new ArrayList<>();
        boolean found = false;
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;

            // Read all rows at once and store them in csvBody
            while ((nextLine = reader.readNext()) != null) {
                csvBody.add(nextLine);
            }

            // Modify the quantity for the given product ID
            for (int i = 1; i < csvBody.size(); i++) { // Start from 1 to skip the header
                if (csvBody.get(i)[0].equals(num)) {
                    int oldQuantity = Integer.parseInt(csvBody.get(i)[1]);
                    csvBody.get(i)[1] = Integer.toString(oldQuantity - 1);
                    found = true;
                    if (oldQuantity < threshold) {
                        overTh = true;
                    }
                    break;
                }
            }

            if (!found) {
                System.out.println("Product ID " + num + " not found.");
            } else {
                // Write the updated data back to the CSV file
                try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
                    writer.writeAll(csvBody);
                    log("Quantity updated successfully.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (overTh) {
            APLFunction fun = new APLFunction("request", aplNum);
            throwEvent(fun, agName);
            log("env> agent " + agName + " wants to request more products with id " + num);

        }
        return null;
    }

    public Term shipProd(String agName, APLNum aplNum) throws ExternalActionFailedException {
        int num = aplNum.toInt();

        log("env> agent " + agName + " wants to ship product with id " + num);

        request("tradeManager"); //at the end of the ship, a new order from the customers will be sent
        return new APLNum(new Integer(1));
    }

    private int findPriceFromCSV(int prodID, String path) {
        int price = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String lastMatchingLine = null;

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 2 && Integer.parseInt(columns[0]) == prodID) {
                    lastMatchingLine = line;
                }
            }

            if (lastMatchingLine != null) {
                String[] columns = lastMatchingLine.split(",");
                price = Integer.parseInt(columns[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return price;
    }
    
    public Term sendPrice(String agName, APLNum id) throws ExternalActionFailedException {
        int prodID = id.toInt();
        String path = "Negotiation_history.csv";
        int p = findPriceFromCSV(prodID, path);
        p+=10; //simple strategy
        log("env> agent " + agName + "proposes the price of: " + p + " for the product with id: " + prodID);
        update_price(prodID, p);
        negotiate(agName, prodID, p);
        return null;
    }

    public void negotiate(String agName, int prodID, int p) {
        String[] choises = { "yes", "no" };
        java.util.Random random = new java.util.Random();
        int random_choise = random.nextInt(choises.length);
        String choise = choises[random_choise];
        log("Accept the price - Customer decision: " + choise);
        if (choise.equals("yes")) {
            APLNum productIDTerm = new APLNum(prodID);
            APLFunction event = new APLFunction("accept", productIDTerm);
            throwEvent(event, agName);
        } else {
            p /=2;
            update_price(prodID, p);
        }

    }
    public void startNegotiation(int prodID) {
        String path = "Negotiation_history.csv";
        log("IN");
        int price = 90;
        // Write to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(prodID + "," + price);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void update_price(int prodID, int price){
        String path = "Negotiation_history.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(prodID + "," + price);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EXPLORE AGENT

    public Term explorePlanet(String agName) throws ExternalActionFailedException {
        log("env> agent " + agName + " is exploring the planet");
        // Randomly 20% send foundResource event, for a ResourceID and a Region
        java.util.Random random = new java.util.Random();
        if (random.nextInt(100) < 10) {
            int resID = random.nextInt(4) + 1;
            int region = random.nextInt(4) + 1;
            APLIdent resourceIDTerm = new APLIdent(Integer.toString(resID));
            APLIdent regionID = new APLIdent(Integer.toString(region));
            APLFunction event = new APLFunction("foundResource", resourceIDTerm, regionID);
            log("env> agent " + agName + " found resource with id " + resID + " in region " + region);
            throwEvent(event, agName);
        } else {
            log("env> agent " + agName + " did not find any resources");
        }
        return null;
    }

    public Term lookForResource(String agName, APLNum resourceID) throws ExternalActionFailedException {
        int resID = resourceID.toInt();
        log("env> agent " + agName + " wants to look for resource with id " + resID);
        java.util.Random random = new java.util.Random();
        if (random.nextInt(100) < 10) {
            int region = random.nextInt(4) + 1;
            APLIdent resourceIDTerm = new APLIdent(Integer.toString(resID));
            APLIdent regionID = new APLIdent(Integer.toString(region));
            APLFunction event = new APLFunction("foundResource", resourceIDTerm, regionID);
            log("env> agent " + agName + " found resource with id " + resID + " in region " + region);
            throwEvent(event, agName);
        } else {
            log("env> agent " + agName + " did not find resource with id " + resID);
        }

        return null;
    }

    public Term mineResource(String agName, APLNum resourceID, APLNum regionID) throws ExternalActionFailedException {
        int resID = resourceID.toInt();
        log("env> agent " + agName + " wants to mine resource with id " + resID + " in region " + regionID);
        // Randomly generate the quantity of the mined resource and add to resourceStock
        java.util.Random random = new java.util.Random();
        int quantity = random.nextInt(10) + 1;
        resourceStock.put(resID, resourceStock.get(resID) + quantity);
        log("env> agent " + agName + " mined " + quantity + " units of resource with id " + resID);
        log("env> Total resource stock: " + resourceStock);

        // Randomly 50% send exhaustedResource event
        if (random.nextBoolean()) {
            log("env> agent " + agName + " exhausted the resource with id " + resID + " in region " + regionID);
            APLIdent resourceIDTerm = new APLIdent(Integer.toString(resID));
            APLFunction event = new APLFunction("exhaustedResource", resourceIDTerm, regionID);
            throwEvent(event, agName);
        }

        // Send the mined quantity to the resource manager
        APLNum minedQuantity = new APLNum(quantity);
        APLFunction event = new APLFunction("extractedQuantity", resourceID, minedQuantity);
        throwEvent(event, agName);

        return null;
    }

    public Term continuousManufacture(String agName) throws ExternalActionFailedException {
        log("> Starting Continuous Manufacture");
        java.util.Random random = new java.util.Random();
        int prodID = random.nextInt(100) + 1;
        APLNum productID = new APLNum(prodID);
        APLFunction event = new APLFunction("continuousManufacture", productID);
        throwEvent(event, agName);
        log("env> agent " + agName + " a continuous manufacture request for product: " + prodID + "----------->");
        return null;

    }

    public Term resourcesQualityCheck(String agName, APLNum ProductID) throws ExternalActionFailedException {
        // Randomly 80% resource quality test passed event for resources of ProductID
        java.util.Random random = new java.util.Random();
        Map<Integer, Integer> components = checkProductFormula(ProductID);
        

        if (random.nextInt(100) < 90) {
            unregisterFromDatabase(resourceStock, ProductID, "C");
            registerToDatabase(processedResources, ProductID, "C");
            APLFunction event = new APLFunction("componentsManufacture", ProductID);
            log("env> agent " + agName + " resource quality evaluation for product: " + ProductID
                    + " --->  Passed  - Continuing manufacturing process ");
            throwEvent(event, agName);
        } else {
            Boolean enoughComponents = checkResourcesAvailability(ProductID);
            if (enoughComponents) {
                unregisterFromDatabase(resourceStock, ProductID, "C");
                registerToDatabase(processedResources, ProductID, "C");
                APLFunction event = new APLFunction("componentsManufacture", ProductID);
                log("env> agent " + agName + " resource quality evaluation for product: " + ProductID
                        + " --->  Failed  - Still available good quality rerources - Continuing manufacturing process.");
                throwEvent(event, agName);
                log("env> agent " + agName + " did not find any resources");
            } else {
                unregisterFromDatabase(processedResources, ProductID, "C");
                log("env> agent " + agName + " resource quality evaluation for product: " + ProductID
                        + " --->  Failed  - Not enough resources to continue manufacturing process - Notifyied priority for this resources - Requeuing product manufacture");
                APLFunction event = new APLFunction("continuousManufacture", ProductID);
                throwEvent(event, agName);
            }
        }

        return null;
    }
    
    public Term componentsManufacturing(String agName, APLNum ProductID) throws ExternalActionFailedException {
        unregisterFromDatabase(processedResources, ProductID, "C");
        registerToDatabase(manufacturedComponents, ProductID, "C");

        log("env> agent " + agName + ", already manufactured components for product: " + ProductID);
        APLFunction event = new APLFunction("assembleProduct", ProductID);
        throwEvent(event, agName);

        return null;
    }

    public Term assembleProduct(String agName, APLNum ProductID) throws ExternalActionFailedException {
        unregisterFromDatabase(manufacturedComponents, ProductID, "C");
        registerToDatabase(assembledProducts, ProductID, "P");

        log("env> agent " + agName + ", new product manufactured: " + ProductID);
        APLFunction event = new APLFunction("finalProductQualityCheck", ProductID);
        throwEvent(event, agName);

        return null;
    }

    public Term finalProductQualityCheck(String agName, APLNum ProductID) throws ExternalActionFailedException {
        
        java.util.Random random = new java.util.Random();
        Map<Integer, Integer> components = checkProductFormula(ProductID);
        
        // Randomly 90% resource quality test passed event for ProductID
        if (random.nextInt(100) < 90) {
            unregisterFromDatabase(assembledProducts, ProductID, "P");
            deliverLogistics(ProductID);
            APLFunction event = new APLFunction("deliverToLogistics", ProductID);
            log("env> agent " + agName + " final product quality evaluation for product: " + ProductID
                    + " --->  Passed  - Delibering to agent logisticsManager.");
            throwEvent(event, agName);
        } else {
                log("env> agent " + agName + " final product quality evaluation for product: " + ProductID
                        + " --->  Failed - Requeuing product manufacture");
                APLFunction event = new APLFunction("continuousManufacture", ProductID);
                throwEvent(event, agName);
          
        }

        return null;
    }
    
    public void unregisterFromDatabase(Map<Integer, Integer> database, APLNum ProductID, String Type)
            throws ExternalActionFailedException {
        log("env> Unregistering data from database: " + database);
        if (Type == "C") {
            Map<Integer, Integer> components = checkProductFormula(ProductID);
            Set<Integer> keys = components.keySet();
            for (Integer key : keys) {
                if (!database.containsKey(key)) {
                    database.put(key, 0);
                }
                else {
                    database.put(key, database.get(key) - components.get(key));
                }
            }
        } else {
            int prodID = ProductID.toInt();
            if (!database.containsKey(prodID)) {
                database.put(prodID, 0);
            }
            else {
                database.put(prodID, database.get(prodID) - 1);
            }
        }
    }

    public void registerToDatabase(Map<Integer, Integer> database, APLNum ProductID, String Type)
            throws ExternalActionFailedException {
        log("env> Registering data from database: " + database);
        if (Type == "C") {
            Map<Integer, Integer> components = checkProductFormula(ProductID);
            Set<Integer> keys = components.keySet();
            for (Integer key : keys) {
                if (!database.containsKey(key)) {
                    database.put(key, components.get(key));
                }
                else {
                    database.put(key, database.get(key) + components.get(key));
                }
            }
        } else {
            int prodID = ProductID.toInt();
            if (!database.containsKey(prodID)) {
                database.put(prodID, 1);
            }
            else {
                database.put(prodID, database.get(prodID) + 1);
            }
        }
    }
    
    
    public Boolean checkResourcesAvailability(APLNum ProductID) throws ExternalActionFailedException {
        boolean enoughComponents = true;
        
        Map<Integer, Integer> components = checkProductFormula(ProductID);
        Set<Integer> keys = components.keySet();

        for (Integer key : keys) {
            if (resourceStock.get(key) < components.get(key)) {
                enoughComponents = false;
                informPriority(key);
            }
        }
        return enoughComponents;
    }


    public Term informPriority(int resID) throws ExternalActionFailedException {
        String agName = "manufacturingManager";
        APLNum resourceID = new APLNum(resID);
        APLFunction event = new APLFunction("manageResourceLack", resourceID);
        throwEvent(event, agName);
        log("env>agent " + agName + " is informing priority for resource: " + resID);
        return null;
    }

    public Term manageProductManufactureRequest(String agName, APLNum ProductID) throws ExternalActionFailedException {
        
        Boolean enoughComponents = checkResourcesAvailability(ProductID);

        if (!enoughComponents) {
            log("env> agent " + agName + " inform: there are not enough resources to manufacture product: "
                    + ProductID + " - requeuing product manufacture");
            APLFunction event = new APLFunction("continuousManufacture", ProductID);
            throwEvent(event, agName);
            return null;
        }
        
        APLFunction event = new APLFunction("resourcesQualityCheck", ProductID);
        throwEvent(event, agName);
        log("env> agent " + agName + " is triggering the manufacture process for product " + ProductID);
        return null;
    }
    
    public Map<Integer, Integer> checkProductFormula(APLNum ProductId) throws ExternalActionFailedException {
        String num = ProductId.toString();
        String path = "src\\Formulas.csv";
        List<String[]> csvBody = new ArrayList<>();
        Map<Integer, Integer> components = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;

            // Read all rows at once and store them in csvBody
            while ((nextLine = reader.readNext()) != null) {
                csvBody.add(nextLine);
            }

            // Modify the quantity for the given product ID
            for (int i = 1; i < csvBody.size(); i++) { // Start from 1 to skip the header
                if (csvBody.get(i)[0].equals(num)) {
                    String componentsString = csvBody.get(i)[1];
                    // Split the componentsString by comma
                    String[] componentsArray = componentsString.split(",");
                    String[] componentsVal1 = componentsArray[0].split(":");
                    String[] componentsVal2 = componentsArray[1].split(":");

                    components.put(Integer.parseInt(componentsVal1[0]), Integer.parseInt(componentsVal1[1]));
                    components.put(Integer.parseInt(componentsVal2[0]), Integer.parseInt(componentsVal2[1]));

                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return components;
    }
    
    public Boolean deliverLogistics(APLNum ProductID) throws ExternalActionFailedException {
        String num = ProductID.toString();
       
        String path = "src\\Inventory.csv";
        List<String[]> csvBody = new ArrayList<>();
        boolean found = false;
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;

            // Read all rows at once and store them in csvBody
            while ((nextLine = reader.readNext()) != null) {
                csvBody.add(nextLine);
            }

            // Modify the quantity for the given product ID
            for (int i = 1; i < csvBody.size(); i++) { // Start from 1 to skip the header
                if (csvBody.get(i)[0].equals(num)) {
                    int oldQuantity = Integer.parseInt(csvBody.get(i)[1]);
                    csvBody.get(i)[1] = Integer.toString(oldQuantity + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Product ID " + num + " not found.");
            } else {
                // Write the updated data back to the CSV file
                try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
                    writer.writeAll(csvBody);
                    log("Quantity updated successfully.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return found;
    }
    
    
}
