function getEmployee() {
    const id = document.getElementById("empid").value;
    const uri = `http://localhost:8080/api/employee/get/${id}`
    const ul = document.getElementById('list-group');

    fetch(uri)
        .then(response => response.json())
        .then(item => {
            ul.innerHTML = '';
            const liId = document.createElement('li');
            liId.classList.add('list-group-item','flex-fill');
            liId.textContent = `Employee ID : ${item.id}`;
            const liFirstName = document.createElement('li');
            liFirstName.classList.add('list-group-item','flex-fill');
            liFirstName.textContent = `First Name : ${item.firstName}`;
            const liLastName = document.createElement('li');
            liLastName.classList.add('list-group-item','flex-fill');
            liLastName.textContent = `Last Name : ${item.lastName}`;
            const liEmail = document.createElement('li');
            liEmail.classList.add('list-group-item','flex-fill');
            liEmail.textContent = `Email ID : ${item.emailId}`;
            const liRole = document.createElement('li');
            liRole.classList.add('list-group-item','flex-fill');
            liRole.textContent = `Role : ${item.role}`;
            const liSupervisor = document.createElement('li');
            liSupervisor.classList.add('list-group-item','flex-fill');
            liSupervisor.textContent = `Supervisor : ${item.supervisor}`;
            const liStatus = document.createElement('li');
            liStatus.classList.add('list-group-item','flex-fill');
            liStatus.textContent = `Status : ${item.status}`;
            ul.appendChild(liId);
            ul.appendChild(liFirstName);
            ul.appendChild(liLastName);
            ul.appendChild(liEmail);
            ul.appendChild(liRole);
            ul.appendChild(liSupervisor);
            ul.appendChild(liStatus)
        })
        .catch(error => {
            alert('Employee Not Found.');
            location.href = "http://localhost:8080/getemployee.html";
            //console.error('Error fetching data:', error);
        });
}
