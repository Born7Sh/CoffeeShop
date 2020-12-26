
from django.contrib import admin
from .models import User
from django.contrib.auth.admin import UserAdmin as BaseUserAdmin
from django.contrib.auth.models import Group

@admin.register(User)
class UserAdmin(admin.ModelAdmin):
    list_display = ('id', 'email', 'name', 'nickname','phone', 'address','cno', 'birth_day', 'gender','r1','r2','r3','r4','r5','r6', 'image','image2','is_wait', 'joined_at', 'last_login_at', 'is_superuser', 'is_active' )
    list_display_links = ('id', 'email')
    exclude = ('password',)                           

    def joined_at(self, obj):
        return obj.date_joined.strftime("%Y-%m-%d")

    def last_login_at(self, obj):
        if not obj.last_login:
            return ''
        return obj.last_login.strftime("%Y-%m-%d %H:%M")

    joined_at.admin_order_field = '-date_joined'      
    joined_at.short_description = 'join_date'
    last_login_at.admin_order_field = 'last_login_at'
    last_login_at.short_description = 'Recent_date'
