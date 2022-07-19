
# Password Manager Application.
### A Java built application to store and save passwords.
<p float="left">
  <img src="  https://i.ibb.co/P41Zp1r/picture1.png" width="350" />
  <img src="https://i.ibb.co/VMQJ9Q8/picture2.png" width="350" />
</p></br>

### Motivation-
A Java program that is made in order to understand the fundementals of password protection and data base management alongside encryption for sensetive data and saving data.
#### Welcome Screen
This is the screen you will be greeted with, each plate of the 4 plates resembles a page in the application itself and contains its own functionality and sole purpose.
![alt text](https://i.ibb.co/P41Zp1r/picture1.png)
### Register Screen
![alt text](https://i.ibb.co/VMQJ9Q8/picture2.png)
### Home Screen

![alt text](https://i.ibb.co/N7v0sqj/picture3.png)
### Credentials Injection
![alt text](https://i.ibb.co/fH4nkvx/picture4.png)


### General Glance-
The application was built using Java and JavaFX Script, SceneBuilder was used in order to easily access and edit the FXML files in the project.

##### Functionality-
The application requires user registeration and login in order to be able to add credintials to the user's profile. the registeration page contains a password generator which works by following general passwords standards to ensure that the user will get the strongest safest password.
the home page contains a plus icon on the bottom right side of the page which on clicking opens a sidebar that will allow the user to inject personal data into the system, upon hitting the save button after inserting the data the given information will be saved in a MYSQL database according to a few sets.
Before saving the password it will be encrypted using **SHA-512** Encryption Algorithm and will be saved in that manner, upon logging in the system will encrypt the inserted password using the same algorithm and check it with the password associated with that specific username.
In addition to the password, the system also saves the application name, a password hint (Encrypted), and a user name.

#### Future Plans
Usage of Symmetric/Asymmetric Encryption Algorithms.
Redirection and logging the user to a specific web page accordingly.
KeyStroke Logging Application Check.
