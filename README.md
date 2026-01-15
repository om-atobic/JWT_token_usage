# JWT_token_usage

inside use the default userPasswordAuthenticationFilter where some default username and passwords are

user
user123

admin
admin123

manager
manager123

Each controller use the user_id from the jwt token if jwt token validation fails then request never reaches 
the other controller
except for the controller /api/auth/** 


