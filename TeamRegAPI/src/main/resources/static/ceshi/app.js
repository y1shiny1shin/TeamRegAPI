document.addEventListener('DOMContentLoaded', () => {
    // 用户登录
    document.getElementById('login-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const userid = document.getElementById('login-userid').value;
        const password = document.getElementById('login-password').value;
        fetch('/api/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userid, password })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('login-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('login-response').textContent = '请求失败';
            });
    });

    // 用户注册
    document.getElementById('register-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const userid = document.getElementById('register-userid').value;
        const password = document.getElementById('register-password').value;
        const username = document.getElementById('register-username').value;
        fetch('/api/user/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userid, password, username })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('register-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('register-response').textContent = '请求失败';
            });
    });

    // 用户修改密码
    document.getElementById('updatepwd-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const userid = document.getElementById('updatepwd-userid').value;
        const password = document.getElementById('updatepwd-password').value;
        const newpassword = document.getElementById('updatepwd-newpassword').value;
        fetch('/api/user/updatepwd', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userid, password, newpassword })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('updatepwd-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('updatepwd-response').textContent = '请求失败';
            });
    });

    // 获取用户信息
    document.getElementById('getuserinfo-btn').addEventListener('click', function() {
        fetch('/api/user/getuserinfo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('getuserinfo-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('getuserinfo-response').textContent = '请求失败';
            });
    });

    // 加入平面设计专用赛道
    document.getElementById('joinpmsjtrace-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const userId = document.getElementById('joinpmsjtrace-userid').value;
        const password = document.getElementById('joinpmsjtrace-password').value;
        const username = document.getElementById('joinpmsjtrace-username').value;
        fetch('/api/team/joinpmsjtrace', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userId, password, username })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('joinpmsjtrace-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('joinpmsjtrace-response').textContent = '请求失败';
            });
    });

    // 踢出队员
    document.getElementById('kickmember-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const teamName = document.getElementById('kickmember-teamName').value;
        const memberName = document.getElementById('kickmember-memberName').value;
        const memberId = document.getElementById('kickmember-memberId').value;
        fetch('/api/team/kickmember', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ teamName, memberName, memberId })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('kickmember-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('kickmember-response').textContent = '请求失败';
            });
    });

    // 退出队伍
    document.getElementById('quitteam-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const teamName = document.getElementById('quitteam-teamName').value;
        fetch('/api/team/quitteam', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ teamName })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('quitteam-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('quitteam-response').textContent = '请求失败';
            });
    });

    // 删除队伍
    document.getElementById('destroy-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const userId = document.getElementById('destroy-userId').value;
        const teamName = document.getElementById('destroy-teamName').value;
        const password = document.getElementById('destroy-password').value;
        fetch('/api/team/destroy', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userId, teamName, password })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('destroy-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('destroy-response').textContent = '请求失败';
            });
    });

    // 新建队伍
    document.getElementById('create-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const teamName = document.getElementById('create-teamName').value;
        const captainName = document.getElementById('create-captainName').value;
        const captainId = document.getElementById('create-captainId').value;
        fetch('/api/team/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ teamName, captainName, captainId })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('create-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('create-response').textContent = '请求失败';
            });
    });

    // 队伍加入赛道
    document.getElementById('groupjoin-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const teamName = document.getElementById('groupjoin-teamName').value;
        const groupType = document.getElementById('groupjoin-groupType').value;
        const captainId = document.getElementById('groupjoin-captainId').value;
        fetch('/api/team/groupjoin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ teamName, groupType, captainId })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('groupjoin-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('groupjoin-response').textContent = '请求失败';
            });
    });

    // 队伍加入
    document.getElementById('join-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const userId = document.getElementById('join-userId').value;
        const teamName = document.getElementById('join-teamName').value;
        fetch('/api/team/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userId, teamName })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('join-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('join-response').textContent = '请求失败';
            });
    });

    // 退出赛道
    document.getElementById('quitgroup-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const groupName = document.getElementById('quitgroup-groupName').value;
        const teamName = document.getElementById('quitgroup-teamName').value;
        fetch('/api/team/quitgroup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ groupName, teamName })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('quitgroup-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('quitgroup-response').textContent = '请求失败';
            });
    });

    // 查询用户所在队伍
    document.getElementById('select-form').addEventListener('submit', function(event) {
        event.preventDefault();
        fetch('/api/team/select', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('select-response').textContent = JSON.stringify(data);
            })
            .catch(error => {
                document.getElementById('select-response').textContent = '请求失败';
            });
    });
});
