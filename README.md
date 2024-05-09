There are 4 core points of this requirement which are:

1. Role-based access control: The system should differentiate between admin and user roles by decoding the Base64-encoded header in all requests.

2. Admin functionality: Admins should have access to the /admin/addUser endpoint, where they can add access permissions for users. The access details should be stored in a file for persistence.

3. User functionality: Users should be able to access the /user/{resource} endpoint, which checks if they have access to the requested resource. Success or failure information should be returned accordingly.

4. Error handling: The system should handle any possible errors that may occur and provide readable error messages in response.

