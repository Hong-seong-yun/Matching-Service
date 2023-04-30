function addTrainer() {
    const addTrainer = $('#traineradd');
    const trainer = new FormData(addTrainer[0]);
    const con_check = confirm("등록하시겠습니까?");
    if (con_check === true) {
        if(!trainer.get('tGender') || !trainer.get('tYear') || !trainer.get('tCareer')) {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        }
        $.ajax({
            type: 'POST',
            url: '/api/traineradd',
            processData: false,
            contentType: false,
    //      List<MultipartFile> = 다중파일로 넘기기
            enctype: 'multipart/form-data',
            data: trainer,
        }).done(function () {
            alert('트레이너가 등록되었습니다.');
            window.location.href = "/home";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};