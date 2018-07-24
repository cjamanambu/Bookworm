![ARCHITECTURE](ARCHITECTURE_Iteration3.png)

## Presentation
> The presentation tier consists of the Activity class and Adapter class which will be used to generate the user interface. The data that will be displayed to user is fetched from logic tier. 

## Business Logic
> The logic tier contains the functional business logic which drives an application’s core capabilities. This tier will interact between presentation and persistence tier. It handles any logic operations, fetch data from persistence tier and send back to presentation if necessary.

## Peresistence
> The persistence tier plays the role of data storage and data access. Data is accessed by the logic layer. 

## Domain Model
> Contains objects that represents business models like an Account or a Book. Each of them contains several attributes and is used by all other tiers throughout the whole project. 
