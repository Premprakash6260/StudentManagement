# StudentManagement

A Spring Boot project for managing students, admins, and courses using JWT-based authentication.

---

## 🔧 Project Setup

- **JDK**: 17
- **Database**: MySQL
- **Dependencies**: Spring Boot, Spring Security, JWT, Lombok, MySQL Driver
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 🧾 Default Admin Credentials

```text
email: admin
password: admin
```

> ⚠️ Delete this default admin once the application is deployed.

---

## 🗂️ application.properties Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

---

## 🔐 Authentication

JWT token is used for authentication across all endpoints.

---

## 📚 API Endpoints

### 🛠️ Admin Endpoints

#### 1. Admin Login

- **Method**: `POST`
- **URL**: `/auth/login`

**Request Body**:

```json
{
  "email": "admin",
  "password": "admin"
}
```

**Response**:

```json
{
  "token": "jwt-token-here"
}
```

---

#### 2. Add New Admin

- **Method**: `POST`
- **URL**: `/auth/admin-registration`

**Request Body**:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

**Response**:

```json
{
  "token": "jwt-token-here"
}
```

---

#### 3. Add New Student

- **Method**: `POST`
- **URL**: `/admin/add-student`

**Request Body**:

```json
{
  "name": "Anaya Reddy",
  "email": "anaya.reddy@example.com",
  "gender": "Female",
  "studentCode": "STU105",
  "dateOfBirth": "29-01-2007",
  "addresses": [
    {
      "addressType": "PERMANENT",
      "street": "34 Rosewood Avenue",
      "city": "Chennai",
      "state": "Tamil Nadu",
      "pincode": "600001"
    },
    {
      "addressType": "CORRESPONDENCE",
      "street": "15 Palm Residency",
      "city": "Coimbatore",
      "state": "Tamil Nadu",
      "pincode": "641001"
    },
    {
      "addressType": "CURRENT",
      "street": "67 Work Street",
      "city": "Bangalore",
      "state": "Karnataka",
      "pincode": "560034"
    }
  ]
}
```

---

#### 4. Add New Course

- **Method**: `POST`
- **URL**: `/admin/add-course`

**Request Body**:

```json
{
  "name": "DataScience",
  "description": "In-depth course on data analysis, machine learning, and statistics using Python.",
  "type": "Technical",
  "duration": 120,
  "topics": [
    "Python",
    "NumPy",
    "Pandas",
    "Data Visualization",
    "Statistics",
    "Machine Learning",
    "Scikit-learn",
    "Deep Learning",
    "Model Deployment"
  ]
}
```

---

#### 5. Assign Course to Student

- **Method**: `POST`
- **URL**: `/admin/assign-course`

**Request Body**:

```json
{
  "studentCode": "STU105",
  "courseName": "DataScience"
}
```

---

#### 6. Search Student by Name

- **Method**: `GET`
- **URL**: `/admin/get-student/Anaya%20Reddy`

---

#### 7. Get Students Assigned to Course

- **Method**: `GET`
- **URL**: `/admin/get-by-course/DataScience`

---

### 🎓 Student Endpoints

#### 1. Student Login

- **Method**: `POST`
- **URL**: `/auth/student-login`

**Request Body**:

```json
{
  "studentCode": "STU105",
  "dateOfBirth": "29-01-2007"
}
```

---

#### 2. Update Profile

- **Method**: `POST`
- **URL**: `/student/update-profile`

**Request Body**:

```json
{
  "email": "aarav.sharma@example.com",
  "mobile": "9876543210",
  "fatherName": "Rajesh Sharma",
  "motherName": "Sunita Sharma",
  "addresses": [
    {
      "id": 1,
      "addressType": "PERMANENT",
      "street": "21 Lotus Residency",
      "city": "Bengaluru",
      "state": "Karnataka",
      "pincode": "560076"
    },
    {
      "id": 2,
      "addressType": "CORRESPONDENCE",
      "street": "12 Orchid Tower",
      "city": "Hyderabad",
      "state": "Telangana",
      "pincode": "500081"
    }
  ]
}
```

---

#### 3. Get Assigned Courses

- **Method**: `GET`
- **URL**: `/student/courses`

---

#### 4. Leave a Course

- **Method**: `DELETE`
- **URL**: `/student/course/DataScience`

---

## 📦 Postman Collections

### Local File

Download from project path: `./Postman/studentmanagement.postman_collection.json`

### Online Link

[🔗 View & Import StudentManagement API Collection](https://ayushjoshi3366.postman.co/workspace/Signimus~cbdbc76d-bdc1-48f9-8a7b-de7ad01b8c80/collection/44470007-33579b83-361c-43bb-80bb-2c4e45fecde0?action=share&creator=44470007)
