# OWNERSHIP
this project is owned by 
**Adhiambo Oyier**
Learning *Java Swing Concepts*

# SET-UP

## Clone Repo
1. Under the repository name, click **Clone or download**.

2. In the Clone with HTTPs section, click :Clone URL button: to copy the clone URL for the repository.

3. Open Git Bash in directory or location where you want the cloned directory to be made.

4. Type `git clone`, and then paste the URL you copied in Step 2.

5. Press **Enter**. Your local clone will be created.

## Create database
1. Open your **phpMyAdmin**
2. Import **vgc_17260.sql** file found in the root directory of the repo.

## Edit database connection properties in java
1. open the `DbConnection.java` file in the `utilities` package of the project, either in your IDE or text-editor
2. edit the following connection properties to your needs:
   -`conn = DriverManager.getConnection("jdbc:mysql://localhost:**portnumber**/vgc_17260", "username", "password");`
3. save the file.
4. in an IDE such as NetBeans, click on the **run** button while the project is open.

# SOME TEST SAMPLES

## A sample admin user credentials for testing are:
>Username: 200
>Password: admin

## Sample faculty credentials are:
>Username: 1234
>Password: faculty

## A sample of student credentials is as follows:
>Username: 15678
>Password: 987654321


