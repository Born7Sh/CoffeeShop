from django.contrib import admin
from backend.models import * 

# Register your models here.


@admin.register(Review)
class ReviewAdmin(admin.ModelAdmin):
    list_display = ('uid', 'review', 'picture', 'cno', 'create_dt')

@admin.register(Notice)
class NoticeAdmin(admin.ModelAdmin):
    list_display = ('title', 'body')

@admin.register(Inquiry)
class InquiryAdmin(admin.ModelAdmin):
    list_display = ('title', 'body')

@admin.register(Cafe)
class CafeAdmin(admin.ModelAdmin):
    list_display = ('cafe_name', 'x', 'y', 'start_time', 'end_time', 'phone', 'notice', 'star', 'com_num', 'tag1', 'tag2', 'business','close', 'seat_curr', 'seat_total')

@admin.register(Check)
class CheckAdmin(admin.ModelAdmin):
    list_display = ('uid', 'start_time', 'end_time', 'sno', 'cno')

@admin.register(Time_Sale)
class Time_SaleAdmin(admin.ModelAdmin):
    list_display = ('name', 'cno', 'rprice', 'sprice')

@admin.register(Booking1)
class Booking1Admin(admin.ModelAdmin):
    list_display = ('cno', 'sno', 't0000', 't0030', 't0100', 't0130', 't0200', 't0230', 't0300', 't0330', 't0400', 't0430', 't0500', 't0530', 't0600', 't0630', 't0700', 't0730', 't0800', 't0830')

@admin.register(Booking2)
class Booking2Admin(admin.ModelAdmin):
    list_display = ('cno', 'sno', 't0900', 't0930', 't1000', 't1030', 't1100', 't1130', 't1200', 't1230', 't1300', 't1330', 't1400', 't1430', 't1500', 't1530', 't1600', 't1630')

@admin.register(Booking3)
class Booking3Admin(admin.ModelAdmin):
    list_display = ('cno', 'sno', 't1700', 't1730', 't1800', 't1830', 't1900', 't1930', 't2000', 't2030', 't2100', 't2130', 't2200', 't2230', 't2300', 't2330')


@admin.register(Img)
class ImgAdmin(admin.ModelAdmin):
    list_display = ('cno','img1','img2','img3','img4','img5')


