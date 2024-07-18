

/*document.getElementById('viewUsersButton').addEventListener('click', function() {
    fetch('https://localhost:8443/users')
    .then(response => response.json())
    .then(users => {
        const usersList = document.getElementById('usersList');
        usersList.innerHTML = '';
        users.forEach(user => {
            const userCard = document.createElement('div');
            userCard.classList.add('user-card');
            userCard.innerHTML = `
                <p><strong>ID:</strong> ${user.id}</p>
                <p><strong>Name:</strong> ${user.name}</p>
                <p><strong>Accounts:</strong></p>
                <ul>
                    ${user.accounts.map(account => `<li>${account}: ${user.accountBalances[account]}</li>`).join('')}
                </ul>
            `;
            usersList.appendChild(userCard);
        });
    })
    .catch(error => {
        alert('Error fetching users: ' + error.message);
    });
});*/

document.getElementById('viewUserButton').addEventListener('click', function() {
    const userId = document.getElementById('userIdInput').value;
    fetch(`https://localhost:8443/users/${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('User not found');
            }
            return response.json();
        })
        .then(user => {
            const usersList = document.getElementById('usersList');
            usersList.innerHTML = '';
            const userCard = document.createElement('div');
            userCard.classList.add('user-card');
            userCard.innerHTML = `
                <p><strong>ID:</strong> ${user.id}</p>
                <p><strong>Name:</strong> ${user.name}</p>
                <p><strong>Accounts:</strong></p>
                <ul>
                    ${user.accounts ? user.accounts.map(account => `<li>${account}: ${user.accountBalances[account]}</li>`).join('') : ''}
                </ul>
            `;
            usersList.appendChild(userCard);
        })
        .catch(error => {
            alert('Error fetching user: ' + error.message);
        });
});

document.getElementById('transferForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const fromAccount = document.getElementById('fromAccount').value;
    const toAccount = document.getElementById('toAccount').value;
    const amount = document.getElementById('amount').value;

    fetch('https://localhost:8443/users/transfer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            fromAccount: fromAccount,
            toAccount: toAccount,
            amount: parseFloat(amount)
        }),
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return response.json().then(error => {
                throw new Error(error.message);
            });
        }
    })
    .then(data => {
        alert('Transfer successful');
        document.getElementById('transferForm').reset();
    })
    .catch(error => {
        alert('Error transferring money: ' + error.message);
    });
    document.getElementById('balanceForm').addEventListener('submit', function(event) {
        event.preventDefault();
    
        const accountNumber = document.getElementById('accountNumber').value;
    
        fetch(`https://localhost:8443/users/balance/${accountNumber}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(error => {
                    throw new Error(error.message);
                });
            }
        })
        .then(data => {
            alert('Account Balance: ' + data.balance);
        })
        .catch(error => {
            alert('Error checking balance: ' + error.message);
        });
    });

    document.getElementById('userAccountNumbersForm').addEventListener('submit', function(event) {
        event.preventDefault();
    
        const userId = document.getElementById('userId').value;
    
        fetch(`https://localhost:8443/${userId}/accounts`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(error => {
                    throw new Error(error.message);
                });
            }
        })
        .then(data => {
            const accountNumbersDiv = document.getElementById('accountNumbers');
            accountNumbersDiv.innerHTML = '';
    
            if (data.length === 0) {
                accountNumbersDiv.innerHTML = '<p>No accounts found for this user.</p>';
            } else {
                const ul = document.createElement('ul');
                data.forEach(accountNumber => {
                    const li = document.createElement('li');
                    li.textContent = accountNumber;
                    ul.appendChild(li);
                });
                accountNumbersDiv.appendChild(ul);
            }
        })
        .catch(error => {
            alert('Error fetching account numbers: ' + error.message);
        });
    });
    async function getUserById() {
        const userId = document.getElementById('userId').value;
        try {
            const response = await fetch(`https://localhost:8443/users/${userId}`);
            if (response.ok) {
                const user = await response.json();
                displayUser(user);
            } else {
                displayError("User not found");
            }
        } catch (error) {
            console.error('Error fetching user:', error);
            displayError("Error fetching user");
        }
    }
    
    function displayUser(user) {
        const userInfoDiv = document.getElementById('userInfo');
        userInfoDiv.innerHTML = `
            <p>ID: ${user.id}</p>
            <p>Name: ${user.name}</p>
            <p>Accounts:</p>
            <ul>
                ${Object.keys(user.accountBalances).map(account => 
                    `<li>${account}: ${user.accountBalances[account]}</li>`
                ).join('')}
            </ul>
        `;
    }
    
    function displayError(message) {
        const userInfoDiv = document.getElementById('userInfo');
        userInfoDiv.innerHTML = `<p>${message}</p>`;
    }
    async function checkAccountBalance() {
        const accountNumber = document.getElementById('accountNumber').value;
        const response = await fetch(`/accounts/${accountNumber}/balance`);
        const balance = await response.json();
        const accountBalance = document.getElementById('accountBalance');
        if (response.ok) {
            accountBalance.innerHTML = `Balance: ${balance}`;
        } else {
            accountBalance.innerHTML = 'Account not found';
        }
    }
    
    
    
    
});
