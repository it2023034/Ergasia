# ΕΡΓΑΣΙΑ GOV GR

---
## Μέλη Ομάδας: 

- it2023034 --> Κουλούρης Ταξιάρχης
- it2023068 --> Σταθόπουλος Νίκος
- it2023095 --> Τσολάκης Βασίλης

---

## Σύντομη Περιγραφή

MyCityGov - Πύλη Αιτημάτων & Ψηφιακών Ραντεβού με τον Δήμο

---

## Χρήσιμες πληροφορίες

Κάθε ρόλος μπορεί να κάνει κανονικό **Login** ή **Login via Gov**, 
αλλά πρόσβαση παρέχεται **μόνο** στις λειτουργίες που του ανήκουν.
Αν είναι πολίτης μπορεί να επιλέξει όποιο από τα δύο επιθυμεί, 
αλλά ο υπάλληλος και ο διαχειριστής μπορούν να συνδεθούν μόνο με το κανονικό **login**.

### Λογαριασμοί για Login:

- Πολίτες (Citizen)
  - **Demo Citizen**
    - username: jkaralis
    - password: test1234
    - afm: 123456789
    - phone: 6989704499
- Υπάλληλοι (Employee)
  - **Demo Employee**
    - username: empl_nikolaou
    - password: emp123
    - afm: 852741963
    - phone: 6941111122
- Διαχειριστές (Admin)
  - **Demo Admin**
    - username: athanasios.krios
    - password: Admin@2026
    - afm: 123406789
    - phone: 2101234567

---

## Χρήσιμα URLs

### Public Pages

| Τίτλος   | URL                            |
|----------|--------------------------------|
| Homepage | http://localhost:8080/homepage |
| Login    | http://localhost:8080/login    |
| Register | http://localhost:8080/register |

### Gov Login Page

| Τίτλος | URL                             | 
|--------|---------------------------------|
| Gov Gr | http://localhost:8081/gov/login |

### Citizen Pages

| Τίτλος         | URL                                     |
|----------------|-----------------------------------------|
| Νέα Αίτηση     | http://localhost:8080/applications/new  |
| Λίστα Αιτήσεων | http://localhost:8080/applications/list |

 - Στη *Λίστα Αιτήσεων* ο πολίτης μπορεί να πατήσει το κουμπί *Προβολή* για να δει τις πληροφορίες της αίτησής του

### Employee Pages

| Τίτλος             | URL                                            |
|--------------------|------------------------------------------------|
| Αιτήματα Υπηρεσίας | http://localhost:8080/employee/requests        |
| Ραντεβού Υπαλλήλου | http://localhost:8080/employee/empappointments |

### Admin Pages

| Τίτλος                | URL                                 |
|-----------------------|-------------------------------------|
| Homepage              | http://localhost:8080/home          |
| Υπηρεσίες / Τμήματα   | http://localhost:8080/departments   |
| Τύποι Αιτημάτων       | http://localhost:8080/request-types |
| Διαθέσιμα Ραντεβού    | http://localhost:8080/appointments  |
| Στατιστικά Συστήματος | http://localhost:8080/statistics    |

---



