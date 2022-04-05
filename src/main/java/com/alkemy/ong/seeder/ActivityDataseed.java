package com.alkemy.ong.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;

import java.time.LocalDateTime;

@Component
public class ActivityDataseed implements CommandLineRunner {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public void run(String... args) {
        loadActivities();
    }

    private void loadActivities() {
        if (activityRepository.count() == 0) {
            Activity activity1 = new Activity(1l,"School support for primary level","Workshops from Monday to Thursday from 10 a.m. to 12 p.m. and from 2 p.m. to 4 p.m. in the counter shift, on Saturdays for boys and girls who attend a double shift school.","https://s3.us-east-2.amazonaws.com/ongsomosmasbucket/primary.jpg", LocalDateTime.now(), LocalDateTime.now());
            Activity activity2 = new Activity(2l,"Secondary school support","Workshops are held from Monday to Friday from 10 a.m. to 12 p.m. and from 4 p.m. to 6 p.m.","https://s3.us-east-2.amazonaws.com/ongsomosmasbucket/highschool.jpg", LocalDateTime.now(),LocalDateTime.now());
            Activity activity3= new Activity(3l,"Tutorship","Program for young people from the third year of secondary school: Weekly meeting with tutors, Project activity, Assistants, School and family accompaniment, Scholarship, Art and Accounts Workshop, Recreational and educational outings","https://s3.us-east-2.amazonaws.com/ongsomosmasbucket/tutorships.jpg",LocalDateTime.now(),LocalDateTime.now());
            activityRepository.save(activity1);
            activityRepository.save(activity2);
            activityRepository.save(activity3);
        }
    }
}
