# AlienCorps

## How to run
Add to the build path the 2apl.jar. You should see the ".classpath" file appearing in your directory with someting like this : 
```
    ...
	<classpathentry kind="lib" path="path/to/2apl/2apl.jar"/>
	...
```
Once you have done this, all the classe of 2apl should be inside the package ```apapl```. 
Now you can create the jar file of the environment: **the main class has to be Env.java**.
Now you can run 2apl through the ```2apl.jar``` file and by opening the ```aliens.mas``` file inside 2apl Platform, it will run the environment.

To run 2apl Platform you have to run the same jar that you used for the build path.


## Cons
There are not enough clear instructions about the creation of environments. It is not mentioned the fact that the syntax of 2apl language is strictly related to blank spaces and that it is case sensitive, meaning that, for instance, ``` productID ``` and ``` ProductID ``` are not the same; the second one is not accepted as a variable. 