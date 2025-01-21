function deleteEmployee(){
   const id = document.getElementById('empid').value;
   const uri = `http://localhost:8080/api/employee/delete/${id}`
   fetch(uri,{method:'DELETE'})
   .then(response => response.text())
   .then(data => {
       alert(data);
       document.getElementById('empid').value = '';
       //location.href = 'http://localhost:8080/allemployees.html';
   })
   .catch(error => console.log(error));
}