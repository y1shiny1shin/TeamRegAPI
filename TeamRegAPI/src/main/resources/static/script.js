document.addEventListener('DOMContentLoaded', function () {
    // 切换模块
    const joinTrackBtn = document.getElementById('joinTrackBtn');
    const createTeamBtn = document.getElementById('createTeamBtn');
    const designTrackBtn = document.getElementById('designTrackBtn');
    const modules = document.querySelectorAll('.module');

    function switchModule(moduleToShow) {
        modules.forEach(module => module.classList.add('hidden'));
        document.getElementById(moduleToShow).classList.remove('hidden');
    }

    joinTrackBtn.addEventListener('click', () => switchModule('joinTrack'));
    createTeamBtn.addEventListener('click', () => switchModule('createTeam'));
    designTrackBtn.addEventListener('click', () => switchModule('designTrack'));

    // 模块一：加入赛道
    document.getElementById('joinTrackForm').addEventListener('submit', function (event) {
        event.preventDefault();
        const data = {
            teamName: document.getElementById('joinTrackTeamName').value,
            groupType: document.getElementById('joinTrackGroupType').value,
            captainId: document.getElementById('joinTrackCaptainId').value
        };

        fetch('/api/team/groupjoin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                alert(result.msg);
                refreshTeamInfo();  // 提交后刷新队伍信息
            })
            .catch(error => console.error('Error:', error));
    });

    // 模块二：创建队伍
    document.getElementById('createTeamForm').addEventListener('submit', function (event) {
        event.preventDefault();
        const data = {
            teamName: document.getElementById('createTeamName').value,
            captainName: document.getElementById('createCaptainName').value,
            captainId: document.getElementById('createCaptainId').value
        };

        fetch('/api/team/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                alert(result.msg);  // 显示接口返回的msg字段
                refreshTeamInfo();  // 提交后刷新队伍信息
            })
            .catch(error => console.error('Error:', error));
    });

    // 模块三：加载并刷新队伍信息
    function refreshTeamInfo() {
        fetch('/api/team/select')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.querySelector('#teamTableFixed tbody');
                tableBody.innerHTML = ''; // 清空表格

                data.forEach(teamData => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${teamData.teamName || ''}</td>
                        <td>${teamData.memberName1 || ''}</td>
                        <td>${teamData.memberId1 || ''}</td>
                        <td>${teamData.memberName2 || ''}</td>
                        <td>${teamData.memberId2 || ''}</td>
                        <td>${teamData.memberName3 || ''}</td>
                        <td>${teamData.memberId3 || ''}</td>
                        <td>${teamData.groupType1 || ''}</td>
                        <td>${teamData.groupType2 || ''}</td>
                        <td>${teamData.groupType3 || ''}</td>
                    `;
                    tableBody.appendChild(row);
                });
            })
            .catch(error => console.error('Error:', error));
    }

    // 模块四：密码匹配检查
    const password = document.getElementById('designTrackPassword');
    const confirmPassword = document.getElementById('designTrackConfirmPassword');
    const submitButton = document.getElementById('designTrackSubmit');

    function checkPasswords() {
        if (password.value && confirmPassword.value && password.value === confirmPassword.value) {
            submitButton.disabled = false;
            submitButton.style.backgroundColor = 'blue';
        } else {
            submitButton.disabled = true;
            submitButton.style.backgroundColor = 'lightgray';
        }
    }

    password.addEventListener('input', checkPasswords);
    confirmPassword.addEventListener('input', checkPasswords);

    // 模块四：提交表单
    document.getElementById('designTrackForm').addEventListener('submit', function (event) {
        event.preventDefault();
        const data = {
            userId: document.getElementById('designTrackUserId').value,
            username: document.getElementById('designTrackUsername').value,
            password: document.getElementById('designTrackPassword').value
        };

        fetch('/api/team/joinpmsjtrace', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                alert(result.msg);
            })
            .catch(error => console.error('Error:', error));
    });

    // 初始化时显示模块二
    switchModule('createTeam');
    refreshTeamInfo();
});
