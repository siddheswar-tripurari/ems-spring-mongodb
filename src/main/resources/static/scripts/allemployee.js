window.onload = function(){
fetch('http://localhost:8080/api/employees')
.then(response => response.json())
.then(items => {
            const itemList = document.getElementById('employee-list');
            items.forEach(item => {
                const tr = document.createElement('tr');
                const tdId = document.createElement('td');
                tdId.textContent = item.id;
                tr.appendChild(tdId);
                const tdFirstName = document.createElement('td');
                tdFirstName.textContent = item.firstName;
                tr.appendChild(tdFirstName);
                const tdLastName = document.createElement('td');
                tdLastName.textContent = item.lastName;
                tr.appendChild(tdLastName);
                const tdEmailId = document.createElement('td');
                tdEmailId.textContent = item.emailId;
                tr.appendChild(tdEmailId);
                const tdRole = document.createElement('td');
                tdRole.textContent = item.role;
                tr.appendChild(tdRole);
                const tdSupervisor = document.createElement('td');
                tdSupervisor.textContent = item.supervisor;
                tr.appendChild(tdSupervisor);
                itemList.appendChild(tr);
            });
        })
        .catch(error => {alert("Error while fetching employees data.")});
};