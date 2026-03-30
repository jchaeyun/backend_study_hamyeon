package backend_study.hamyeon.problem.dto;
//프엔이 이 데이터를 받아 "고민 작성하기" 화면을 띄울지, "진행 중인 화분" 화면을 띄울지 결정
//서버->클라
public record HomeResponseDto(
        boolean hasActiveProblem, //진행중인 고민 여부
        Long problemId, //고민 Id(존재할 경우)
        String title, //고민 제목(존재할 경우)
        long remainingHours //남은 시간(시간 단위)
) {
    //고민이 없을 때 반환할 정적 팩토리 메서드
    public static HomeResponseDto empty(){
        return new HomeResponseDto(false,null,null,0);
    }
}
