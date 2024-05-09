There are 4 core points of this requirement which are:

1. Role-based access control: The system should differentiate between admin and user roles by decoding the Base64-encoded header in all requests.

2. Admin functionality: Admins should have access to the /admin/addUser endpoint, where they can add access permissions for users. The access details should be stored in a file for persistence.

3. User functionality: Users should be able to access the /user/{resource} endpoint, which checks if they have access to the requested resource. Success or failure information should be returned accordingly.

4. Error handling: The system should handle any possible errors that may occur and provide readable error messages in response.

=======================================================================================

To address these issues, I mainly followed the following 4 steps:

1. Setting up interceptors: For each incoming Base64-encoded information, I implemented interceptors to filter and block any invalid identity information. Additionally, I implemented interception for URLs that only admin users are allowed to access, specifically targeting regular user accounts.

2. Project structure: The project is divided into different layers including controller, service, interceptor, common, and entity, with each layer having its own defined scope and responsibilities.

3. Writing logic processing code: The logic processing code was written to handle various functionalities and operations within the system.

4. Writing unit test code: To ensure robustness, I wrote unit tests to cover different possible scenarios and potential bugs. Each type of potential error was assigned a specific code and value, ensuring efficient and prompt communication between the frontend and backend systems.

Each test case displays error information effectively, and below I will show a few examples of these error messages.

![image](https://github.com/Gloria-gg/managerSystem/assets/67083942/b35cd362-1ec7-4d6a-a83a-2c56c6ee482d)

![image](https://github.com/Gloria-gg/managerSystem/assets/67083942/31ac0cb7-8d5f-49f2-b45b-98d144d9897e)

![image](https://github.com/Gloria-gg/managerSystem/assets/67083942/8674c502-fe49-4450-8367-d6bda95fc638)

