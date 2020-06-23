## Pal-Tracker

### Curl Commands

- Get all time entries <br/> 
  ```
  curl -i localhost:8080/time-entries
  ```
  
- Create a time entry <br/> 
  ```
  curl -i -XPOST -H"Content-Type: application/json" localhost:8080/time-entries -d"{\"projectId\": 1, \"userId\": 2, \"date\": \"2019-01-01\", \"hours\": 8}"
  ```
  
- Get a time entry by ID
  ```
  curl -i localhost:8080/time-entries/${TIME_ENTRY_ID}
  ```

- Update a time entry by ID
  ```  
  curl -i -XPUT -H"Content-Type: application/json" localhost:8080/time-entries/${TIME_ENTRY_ID} -d"{\"projectId\": 88, \"userId\": 99, \"date\": \"2019-01-01\", \"hours\": 8}"
  ```
  
- Delete a time entry by ID
  ```
  curl -i -XDELETE -H"Content-Type: application/json" localhost:8080/time-entries/${TIME_ENTRY_ID}
  ```
  