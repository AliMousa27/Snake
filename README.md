# Snake
This is my first project and I decided to create a snake game by referencing javafx libraries.
# How it looks
![image](https://user-images.githubusercontent.com/114988369/212557429-ac87cc1a-7b00-4176-9c1d-6006a4ccb039.png)

![image](https://user-images.githubusercontent.com/114988369/212557309-2c508682-4c23-4604-bf0f-983477112e0a.png)
# Features
* Vibrant colors using simple css.
* Ability to change controls

* Color customization

![image](https://user-images.githubusercontent.com/114988369/212557413-9fc43a6b-234c-49cb-99d9-234c5c61079f.png)
* customization for grid size and game speed
# Cloning process:
1. Navigate the directory to be at the desktop 

<img width="164" alt="image" src="https://user-images.githubusercontent.com/114988369/212747407-0580196c-9adc-4ee6-9213-70f48affeab2.png">

2. Clone the repositry with the git clone command into your desktop

3. Download Javafx if you havent already

4. Open the project with your IDE of choice. I am using VSCode

5. Add the library dependancies. Open the "App.java" then on the bottom left corener. there is a secton called "Java projects" Open that and press the + sing in refrenced libraries

<img width="195" alt="image" src="https://user-images.githubusercontent.com/114988369/212747748-d01213c8-9f4c-4537-85c4-b053660a77b2.png">

6. navigate to where you have the javafx lib folder installed and select all.

7. In the grouped buttons at the top, select "Run" and then add configuration 

<img width="317" alt="image" src="https://user-images.githubusercontent.com/114988369/212747977-dbe204d5-f4d8-4964-87bd-39bb24108a24.png">

8. in the launch.json add the following command
"vmArgs": "--module-path PathToWhereYouHaveJavafxLib --add-modules javafx.controls,javafx.fxml"
Note: 
 The process may differ between IDE's and OSS'. For more information about adding the javafx library see https://openjfx.io/openjfx-docs/
