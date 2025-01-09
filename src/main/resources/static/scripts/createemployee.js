document.getElementById('employeeForm').addEventListener('submit',function(event){
    event.preventDefault();
    const id = Number(document.getElementById('empid').value);
    const firstName = document.getElementById('fname').value;
    const lastName = document.getElementById('lname').value;
    const emailId = document.getElementById('email').value;
    const role = document.getElementById('role').value;
    const supervisor = document.getElementById('supervisor').value;
    const obj = {id:id,firstName,lastName,emailId,role,supervisor};

    fetch("http://localhost:8080/api/employee/create",{method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(obj)
    })
    .then(response => response.text())
    .then(data => {
       alert(data);
       //location.href = 'http://localhost:8080/allemployees.html';
    })
    .catch(error => {
       alert('Error while creating employee. ' + error);
    })

})