package com.example.user;

import android.graphics.Bitmap;

public class ReviewData {
    private String ReviewDate;
    private String ReviewId;
    private String ReviewAge;
    private String ReviewGender;
    private String ReviewGrade;
    private String Review;
    private Bitmap ReviewPicture;

    public ReviewData(String ReviewId, String ReviewAge, String ReviewGender , String ReviewDate, String Review, String ReviewGrade) {
        this.Review = Review;
        this.ReviewAge = ReviewAge;
        this.ReviewGender = ReviewGender;
        this.ReviewDate = ReviewDate;
        this.ReviewGrade = ReviewGrade;
        this.ReviewId = ReviewId;
    }
    public ReviewData(String ReviewId, String ReviewAge, String ReviewGender , String ReviewDate, String Review, String ReviewGrade,Bitmap ReviewPicture) {
        this.Review = Review;
        this.ReviewAge = ReviewAge;
        this.ReviewGender = ReviewGender;
        ReviewDate = ReviewDate.substring(0,10);
        this.ReviewDate = ReviewDate;
        this.ReviewGrade = ReviewGrade;
        this.ReviewId = ReviewId;
        this.ReviewPicture = ReviewPicture;
    }

    public String getReview() {
        return Review;
    }



    public String getReviewAge() {
        return ReviewAge;
    }

    public String getReviewDate() {
        return ReviewDate;
    }

    public String getReviewGender() {
        return ReviewGender;
    }

    public String getReviewGrade() {
        return ReviewGrade;
    }

    public String getReviewId() {
        return ReviewId;
    }
    public Bitmap getReviewPicture(){
        return this.ReviewPicture;
    }

    public void setReview(String review) {
        Review = review;
    }

    public void setReviewAge(String reviewAge) {
        ReviewAge = reviewAge;
    }

    public void setReviewDate(String reviewDate) {
        ReviewDate = reviewDate;
    }

    public void setReviewGender(String reviewGender) {
        ReviewGender = reviewGender;
    }

    public void setReviewGrade(String reviewGrade) {
        ReviewGrade = reviewGrade;
    }

    public void setReviewId(String reviewId) {
        ReviewId = reviewId;
    }

    public void setReviewPicture(Bitmap reviewPicture){
        this.ReviewPicture = reviewPicture;
    }
}