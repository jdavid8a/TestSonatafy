# TestSonatafy

For the development of the code challenge, the first step was to read the exercise a couple of times to understand the requirements. Once understood, I did the following:

Based on my understanding, I decided to use a hashmap in the main method to load the objects since I needed it to allow null values.

I created the "ChangeType" interface and the "PropertyUpdate" and "ListUpdate" classes, where changes to properties and added or removed properties would be stored.
I created the "DiffTool" class with a public attribute "List<ChangeType> changes;" which would be returned later.
I created the "diff" method, which is responsible for storing the changes. To analyze the two objects, I created the "diffObjects" method where the current and previous objects and the property would be analyzed.
For internal lists, I created the "Services" class that extends ArrayList.
Since I needed to analyze list and map objects, I had to identify the instance class of the object to iterate through and compare them.
To meet the last requirement, I created the custom "AuditKey" annotation and added the "id" attribute to the "Services" class.
The "validateObject" function was added to the "DiffTool" class to validate, using Java reflection, that the annotation or the "id" attribute is present.
I modified the function to add the validation, for which I also created the "IdFieldOrAuditKeyException" class that inherits from Exception and throws the custom exception. I also added the "validateIdList" variable to the class to enable or disable the validation of the "id" field or the "auditkey" annotation.
Finally, I took the time to comment the code a bit to make it clearer, and in case someone needs to make changes, the review process would be easier.
Once documented in comments, I proceeded to run tests with different data on the objects to be compared from the main method.
The development took me around 8 hours, with an additional 8 hours spent on adjustments and testing.




