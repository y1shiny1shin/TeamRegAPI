// 表单交互逻辑
const joinpmsjForm = document.getElementById("joinpmsjForm");
const joinpmsjSubmit = document.getElementById("joinpmsjSubmit");

joinpmsjForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const formData = {
        userId: document.getElementById("userId1").value,
        password: document.getElementById("password1").value,
        username: document.getElementById("username1").value,
        confirmPassword: document.getElementById("confirmPassword1").value,
    };

    $.ajax({
        type: "POST",
        url: "/api/team/joinpmsjtrace",
        data: JSON.stringify(formData),
        contentType: "application/json",
        processData: false,
        success: (data) => {
            console.log(data.msg);
        },
        error: (xhr, status, error) => {
            console.error(xhr, status, error);
        },
    });
});

const createTeamForm = document.getElementById("createTeamForm");
const createTeamSubmit = document.getElementById("createTeamSubmit");

createTeamForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const formData = {
        teamName: document.getElementById("teamName1").value,
        captainName: document.getElementById("captainName1").value,
        captainId: document.getElementById("captainId1").value,
    };

    $.ajax({
        type: "POST",
        url: "/api/team/create",
        data: JSON.stringify(formData),
        contentType: "application/json",
        processData: false,
        success: (data) => {
            console.log(data.msg);
        },
        error: (xhr, status, error) => {
            console.error(xhr, status, error);
        },
    });
});

// 选赛道弹出框
const joinGroupModal = document.getElementById("joinGroupModal");

document.getElementById("joinGroup").addEventListener("click", () => {
    const formData = {
        teamName: document.getElementById("teamName").value,
        groupType: document.getElementById("groupType").value,
        captainId: document.getElementById("captainId").value,
    };

    $.ajax({
        type: "POST",
        url: "/api/team/groupjoin",
        data: JSON.stringify(formData),
        contentType: "application/json",
        processData: false,
        success: (data) => {
            console.log(data.msg);
        },
        error: (xhr, status, error) => {
            console.error(xhr, status, error);
        },
    });

    joinGroupModal.showModal();
});

// 表格交互逻辑
const table = document.getElementById("table");

//...

// 退出赛道接口
document.getElementById("quitGroup").addEventListener("click", () => {
    const formData = {
        groupName: document.getElementById("groupName1").value,
        teamName: document.getElementById("teamName1").value,
    };

    $.ajax({
        type: "POST",
        url: "/api/team/quitgroup",
        data: JSON.stringify(formData),
        contentType: "application/json",
        processData: false,
        success: (data) => {
            console.log(data.msg);
        },
        error: (xhr, status, error) => {
            console.error(xhr, status, error);
        },
    });
});

// 解散队伍接口
document.getElementById("destroyTeam").addEventListener("click", () => {
    const formData = {
        userId: document.getElementById("userId1").value,
        teamName: document.getElementById("teamName1").value,
        password: document.getElementById("password1").value,
        confirmPassword: document.getElementById("confirmPassword1").value,
    };

    $.ajax({
        type: "POST",
        url: "/api/team/destroy",
        data: JSON.stringify(formData),
        contentType: "application/json",
        processData: false,
        success: (data) => {
            console.log(data.msg);
        },
        error: (xhr, status, error) => {
            console.error(xhr, status, error);
        },
    });
});

// 退出队伍弹出框
const quitTeamModal = document.getElementById("quitTeamModal");

document.getElementById("quitTeam").addEventListener("click", () => {
    quitTeamModal.showModal();
});

// 加入队伍接口
document.getElementById("joinTeam").addEventListener("click", () => {
    const formData = {
        userId: document.getElementById("userId2").value,
        teamName: document.getElementById("teamName3").value,
    };

    $.ajax({
        type: "POST",
        url: "/api/team/join",
        data: JSON.stringify(formData),
        contentType: "application/json",
        processData: false,
        success: (data) => {
            console.log(data.msg);
        },
        error: (xhr, status, error) => {
            console.error(xhr, status, error);
        },
    });
});
