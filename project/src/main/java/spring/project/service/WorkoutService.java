package spring.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import spring.project.domain.Workout;
import spring.project.repository.WorkoutRepository;

import java.util.List;
import java.util.Optional;

public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository){
        this.workoutRepository = workoutRepository;
    }

    //전체 workout 리스트 가져오기
    public List<Workout> getAllWorkoutList(){
        return workoutRepository.findAll();
    }

    //운동 추가, 신청
    public void addWorkout(Workout workout){
        if(validateDuplicateWorkout(workout)) {
            workoutRepository.save(workout);
        } else {
            throw new RuntimeException("이미 같은 운동이 존재합니다.");
        }
    }

    //운동 업데이트
    public Workout updateWorkout(int workoutId, Workout updatedWorkout){
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);

        if(optionalWorkout.isPresent()){
            //Workout이 존재할 경우에만 수정
            Workout workout = optionalWorkout.get();
            //수정된 내용으로 업데이트
            workout.setWorkoutType(updatedWorkout.getWorkoutType());
            workout.setWorkoutDifficulty(updatedWorkout.getWorkoutDifficulty());
            workout.setTrainerId(updatedWorkout.getTrainerId());
            workout.setTrainerName(updatedWorkout.getTrainerName());
            //업데이트 된 Workout 저장 후 반환
            return workoutRepository.save(workout);
        } else {
            return null;  //조회된 Workout 없을 경우
        }
    }

    //운동 삭제
    public void deleteWorkout(int workoutId){
        //workoutId 를 사용하여 해당 Workout을 DB에서 조회
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);

        if(optionalWorkout.isPresent()){
            //workout이 존재할 경우
            Workout workout = optionalWorkout.get();
            //workout 삭제
            workoutRepository.delete(workout);
        } else {
            throw new RuntimeException("존재하지 않는 Entity 입니다");
        }
    }

    //운동 중복신청 검사 (WorkoutId(운동 식별자)로 겹치는지 검사)
    public boolean validateDuplicateWorkout(Workout workout){
        //workoutId 식별자로 이미 존재하는 Workout을 조회
        Optional<Workout> existingWorkout =
                Optional.ofNullable(workoutRepository.findByWorkoutId(workout.getWorkoutId()));

        //이미 존재하는 Workout이 있으면 중복이니까
        if (existingWorkout.isPresent()) {
            return false;
        }
        else { //같은 운동 없음.
            return true;
        }
    }

    // userId로 Workout 리스트 가져오기
    public List<Workout> getWorkoutsByUserId(String userId) {
        return workoutRepository.findByUserId(userId);
    }

    // 특정 키워드로 Workout 리스트 검색
    public List<Workout> findWorkoutsByKeyword(String keyword) {
        return workoutRepository.findByKeyword(keyword);
    }

}
