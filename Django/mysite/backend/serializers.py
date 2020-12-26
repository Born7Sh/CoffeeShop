from rest_framework import serializers, viewsets
from .models import *
from user.models import *
class ReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = Review
        fields = '__all__'

class AgeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Age
        fields = '__all__'

class GenderSerializer(serializers.ModelSerializer):
    class Meta:
        model = Gender
        fields = '__all__'


class NoticeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Notice
        fields = '__all__'

class InquirySerializer(serializers.ModelSerializer):
    class Meta:
        model = Inquiry
        fields = '__all__'

class CafeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Cafe
        fields = '__all__'

class CheckSerializer(serializers.ModelSerializer):
    class Meta:
        model = Check
        fields = '__all__'

class TimeSaleSerializer(serializers.ModelSerializer):
    class Meta:
        model = Time_Sale
        fields = '__all__'

class Booking1Serializer(serializers.ModelSerializer):
    class Meta:
        model = Booking1
        fields = '__all__'

class Booking2Serializer(serializers.ModelSerializer):
    class Meta:
        model = Booking2
        fields = '__all__'

class Booking3Serializer(serializers.ModelSerializer):
    class Meta:
        model = Booking3
        fields = '__all__'

class Booking3Serializer(serializers.ModelSerializer):
    class Meta:
        model = Booking3
        fields = '__all__'

class FavoriteSerializer(serializers.ModelSerializer):
    class Meta:
        model = Favorite
        fields = '__all__'

class GuestSerializer(serializers.ModelSerializer):
    class Meta:
        model = Guest
        fields = '__all__'

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = 'id'

class ImgSerializer(serializers.ModelSerializer):
    class Meta:
        model = Img
        fields = '__all__'
