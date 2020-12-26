from django.shortcuts import render
from django.views.generic import TemplateView
from django.views.generic import CreateView, FormView
from django.contrib.auth.forms import UserCreationForm, PasswordResetForm
from django.contrib.auth import login, authenticate
from django.urls import reverse_lazy
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse, JsonResponse
from rest_framework import generics, mixins, viewsets
from rest_framework.decorators import api_view
from rest_framework.parsers import JSONParser
from .models import *
from .serializers import *
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework import filters
#user
from django.contrib.auth import get_user_model
from django.contrib.auth.tokens import default_token_generator
from mysite import settings
from .forms import VerificationEmailForm
from django.contrib import messages
from .mixins import VerifyEmailMixin
from user.models import *
import math
from django_filters import rest_framework as filters1
# Create your views here.


@api_view(['GET'])
def review(request):
    reviews = Review.objects.all()
    serializers = serializers.serialize('json',reviews)
    return Response(serializers.data)

@csrf_exempt
def ReviewList(self, request, pk):
    if request.method == 'GET':
        queryset = Review.objects.all()
        serializer = ReviewSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = ReviewSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

@api_view(['GET'])
def age(request):
    ages = Age.objects.all()
    serializers = serializers.serialize('json',ages)
    return Response(serializers.data)

@csrf_exempt
def AgeList(self, request, pk):
    if request.method == 'GET':
        queryset = Age.objects.all()
        serializer = ReviewSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = AgeSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

@api_view(['GET'])
def gender(request):
    genders = Gender.objects.all()
    serializers = serializers.serialize('json',genders)
    return Response(serializers.data)

@csrf_exempt
def GenderList(self, request, pk):
    if request.method == 'GET':
        queryset = Gender.objects.all()
        serializer = GenderSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = GenderSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)


class ReviewListView(generics.ListAPIView):
    queryset = Review.objects.all()
    serializer_class = ReviewSerializer
    filter_backends = [filters1.DjangoFilterBackend,]
    filterset_fields = ['uid', 'cno']

@api_view(['GET'])
def notice(request):
    notices = Notice.objects.all()
    serializers = serializers.serialize('json',notices)
    return Response(serializers.data)

@csrf_exempt
def NoticeList(self, request, pk):
    if request.method == 'GET':
        queryset = Notice.objects.all()
        serializer = NoticeSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = NoticeSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

@api_view(['GET'])
def inquiry(request):
    notices = Inquiry.objects.all()
    serializers = serializers.serialize('json',inquiry)
    return Response(serializers.data)

@csrf_exempt
def InquiryList(self, request, pk):
    if request.method == 'GET':
        queryset = inquiry.objects.all()
        serializer = InquirySerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = InquirySerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)


@api_view(['GET'])
def cafe(request):
    cafes = Cafe.objects.all()
    serializers = serializers.serialize('json',cafes)
    return Response(serializers.data)

@csrf_exempt
def CafeList(self, request, pk):
    if request.method == 'GET':
        queryset = Cafe.objects.all()
        serializer = CafeSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = CafeSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)
 
class CafeListView(generics.ListAPIView):
    queryset = Cafe.objects.all()
    serializer_class = CafeSerializer
    filter_backends = [filters.OrderingFilter]
    ordering_fields = ['business']

class CafesList(generics.ListAPIView):
    queryset = Cafe.objects.all()
    serializer_class = CafeSerializer
    filter_backends = [DjangoFilterBackend]
    filterset_fields = ['cafe_name', 'x', 'y', 'start_time', 'end_time', 'phone', 'notice', 'star', 'com_num', 'business', 'seat_curr', 'seat_total']



@api_view(['DELETE'])
#def cafe_delete(self, request, pk):
#    cafe.delete()
#    return Response({'message':'cafe has been deleted!'})

@api_view(['GET'])
def check(request):
    checks = Check.objects.all()
    serializers = serializers.serialize('json',checks)
    return Response(serializers.data)

@csrf_exempt
def CheckList(self, request, pk):
    if request.method == 'GET':
        queryset = Check.objects.all()
        serializer = CheckSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = CheckSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

class CheckListView(generics.ListAPIView):
    queryset = Check.objects.all()
    serializer_class = CheckSerializer
    filter_backends = [filters.OrderingFilter]
    ordering_fields = ['start_time']


class CheckListView(generics.ListAPIView):
    queryset = Check.objects.all()
    serializer_class = CheckSerializer
    filter_backends = (filters1.DjangoFilterBackend,)
    filterset_fields = ['uid','phone', 'start_time', 'end_time', 'sno', 'cno','start_date','end_date']



@api_view(['GET'])
def timesale(request):
    timesales = Time_Sale.objects.all()
    serializers = serializers.serialize('json',timesales)
    return Response(serializers.data)

@csrf_exempt
def TimeSaleList(self, request, pk):
    if request.method == 'GET':
        queryset = Time_Sale.objects.all()
        serializer = TimeSaleSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = TimeSaleSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

class TimeSaleListView(generics.ListAPIView):
    queryset = Time_Sale.objects.all()
    serializer_class = TimeSaleSerializer
    filter_backends = [filters1.DjangoFilterBackend,]
    filterset_fields = ['cno']


@api_view(['GET'])
def booking1(request):
    booking1s = Booking1.objects.all()
    serializers = serializers.serialize('json',booking1s)
    return Response(serializers.data)

@csrf_exempt
def Booking1List(self, request, pk):
    if request.method == 'GET':
        queryset = Booking1.objects.all()
        serializer = Booking1Serializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = Booking1Serializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)


class Booking1ListView(generics.ListAPIView):
    queryset = Booking1.objects.all()
    serializer_class = Booking1Serializer
    filter_backends = [filters1.DjangoFilterBackend,]
    filterset_fields = ['uid','phone','cno','sno', 't0000', 't0030', 't0100', 't0130', 't0200', 't0230', 't0300', 't0330', 't0400', 't0430','t0500','t0530','t0600','t0630','t0700','t0730','t0800','t0830']


@api_view(['GET'])
def booking2(request):
    booking2s = Booking2.objects.all()
    serializers = serializers.serialize('json',booking2s)
    return Response(serializers.data)

@csrf_exempt
def Booking2List(self, request, pk):
    if request.method == 'GET':
        queryset = Booking2.objects.all()
        serializer = Booking2Serializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = Booking2Serializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

class Booking2ListView(generics.ListAPIView):
    queryset = Booking2.objects.all()
    serializer_class = Booking2Serializer
    filter_backends = [filters1.DjangoFilterBackend,]
    filterset_fields = ['uid','phone','cno','sno', 't0900', 't0930', 't1000', 't1030', 't1100', 't1130', 't1200', 't1230', 't1300', 't1330', 't1400', 't1430', 't1500', 't1530', 't1600', 't1630']


@api_view(['GET'])
def booking3(request):
    booking3s = Booking3.objects.all()
    serializers = serializers.serialize('json',booking3s)
    return Response(serializers.data)

@csrf_exempt
def Booking3List(self, request, pk):
    if request.method == 'GET':
        queryset = Booking3.objects.all()
        serializer = Booking3Serializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = Booking3Serializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

class Booking3ListView(generics.ListAPIView):
    queryset = Booking3.objects.all()
    serializer_class = Booking3Serializer
    filter_backends = [filters1.DjangoFilterBackend,]
    filterset_fields = ['uid','phone','cno', 'sno', 't1700', 't1730', 't1800', 't1830', 't1900', 't1930', 't2000', 't2030', 't2100', 't2130', 't2200', 't2230', 't2300', 't2330']


@api_view(['GET'])
def favorite(request):
    favorites = Favorite.objects.all()
    serializers = serializers.serialize('json',favorites)
    return Response(serializers.data)

@csrf_exempt
def FavoriteList(self, request, pk):
    if request.method == 'GET':
        queryset = Favorite.objects.all()
        serializer = FavoriteSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = FavoriteSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)
"""
    elif request.method == 'DELETE':
        event = self.get_object(pk)
        event.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
"""

class FavoriteListView(generics.ListAPIView):
    queryset = Favorite.objects.all()
    serializer_class = FavoriteSerializer
    filter_backends = [filters1.DjangoFilterBackend,]
    filterset_fields = ['uid', 'cno']



@api_view(['GET'])
def guest(request):
    guests = Guest.objects.all()
    serializers = serializers.serialize('json',guests)
    return Response(serializers.data)

@csrf_exempt
def GuestList(self, request, pk):
    if request.method == 'GET':
        queryset = Guest.objects.all()
        serializer = GuestSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = GuestSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

@api_view(['GET'])
def img(request):
    imgs = Img.objects.all()
    serializers = serializers.serialize('json',imgs)
    return Response(serializers.data)

@csrf_exempt
def ImgList(self, request, pk):
    if request.method == 'GET':
        queryset = Img.objects.all()
        serializer = ImgSerializer(query_set, many = True)
        return JsonResponse(serializer.data, safe = False)
    
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = ImgSerializer(data=data)
        if serializer.is_vaild():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

class ImgListView(generics.ListAPIView):
    queryset = Img.objects.all()
    serializer_class = ImgSerializer
    filter_backends = [filters1.DjangoFilterBackend,]
    filterset_fields = ['cno']


"""
@api_view(['PUT'])
def review_put(request,pk):
    if requset.method == 'PUT':
        serializer = ReviewSerializer(Review, data=request.DATA)
        if serializer.is_vaild():
            serializer.save()
            return Response(serializer.data)
        else:
            return Response(serializer.erros, status=status.HTTP_400_BAD_REQUEST)
"""
#Router
"""
class ReviewViewSet(viewsets.ModelViewSet):
    queryset = Review.objects.all()
    serializer_class = ReviewSerializer

class CafeViewSet(viewsets.ModelViewSet):
    query_set = Cafe.objects.all()
    serializer_class = CafeSerializer

class CheckViewSet(viewsets.ModelViewSet):
    query_set = Check.objects.all()
    serializer_class = CheckSerializer

class Time_SaleViewSet(viewsets.ModelViewSet):
    query_set = Time_Sale.objects.all()
    serializer_class = Time_SaleSerializer

class Booking1ViewSet(viewsets.ModelViewSet):
    query_set = Booking1.objects.all()
    serializer_class = Booking1Serializer

class Booking2ViewSet(viewsets.ModelViewSet):
    query_set = Booking2.objects.all()
    serializer_class = Booking2Serializer

class Booking3ViewSet(viewsets.ModelViewSet):
    query_set = Booking3.objects.all()
    serializer_class = Booking3Serializer
"""

#Serializer
class ReviewsList(generics.ListCreateAPIView):
    queryset = Review.objects.all()
    serializer_class = ReviewSerializer

class ReviewsDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Review.objects.all()
    serializer_class = ReviewSerializer

class AgesList(generics.ListCreateAPIView):
    queryset = Age.objects.all()
    serializer_class = AgeSerializer

class AgesDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Age.objects.all()
    serializer_class = AgeSerializer

class GendersList(generics.ListCreateAPIView):
    queryset = Gender.objects.all()
    serializer_class = GenderSerializer

class GendersDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Gender.objects.all()
    serializer_class = GenderSerializer


class NoticesList(generics.ListCreateAPIView):
    queryset = Notice.objects.all()
    serializer_class = NoticeSerializer

class NoticesDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Notice.objects.all()
    serializer_class = NoticeSerializer

class InquirysList(generics.ListCreateAPIView):
    queryset = Inquiry.objects.all()
    serializer_class = InquirySerializer

class InquirysDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Inquiry.objects.all()
    serializer_class = InquirySerializer


class CafesList(generics.ListCreateAPIView):
    queryset = Cafe.objects.all()
    serializer_class = CafeSerializer

class CafesDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Cafe.objects.all()
    serializer_class = CafeSerializer

class ChecksList(generics.ListCreateAPIView):
    queryset = Check.objects.all()
    serializer_class = CheckSerializer

class ChecksDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Check.objects.all()
    serializer_class = CheckSerializer

class TimeSalesList(generics.ListCreateAPIView):
    queryset = Time_Sale.objects.all()
    serializer_class = TimeSaleSerializer

class TimeSalesDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Time_Sale.objects.all()
    serializer_class = TimeSaleSerializer

class Booking1sList(generics.ListCreateAPIView):
    queryset = Booking1.objects.all()
    serializer_class = Booking1Serializer

class Booking1sDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Booking1.objects.all()
    serializer_class = Booking1Serializer

class Booking2sList(generics.ListCreateAPIView):
    queryset = Booking2.objects.all()
    serializer_class = Booking2Serializer

class Booking2sDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Booking2.objects.all()
    serializer_class = Booking2Serializer

class Booking3sList(generics.ListCreateAPIView):
    queryset = Booking3.objects.all()
    serializer_class = Booking3Serializer
    
class Booking3sDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Booking3.objects.all()
    serializer_class = Booking3Serializer

class FavoritesList(generics.ListCreateAPIView):
    queryset = Favorite.objects.all()
    serializer_class = FavoriteSerializer
    
class FavoritesDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Favorite.objects.all()
    serializer_class = FavoriteSerializer


class GuestsList(generics.ListCreateAPIView):
    queryset = Guest.objects.all()
    serializer_class = GuestSerializer

class GuestsDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Guest.objects.all()
    serializer_class = GuestSerializer

class ImgsList(generics.ListCreateAPIView):
    queryset = Img.objects.all()
    serializer_class = ImgSerializer
    
class ImgsDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Img.objects.all()
    serializer_class = ImgSerializer


#apk template
class ApkDownloadView(TemplateView):
    template_name = 'apk.html'


class GramView(TemplateView):
    template_name = 'user/14gram.html'

class PrivateView(TemplateView):
    template_name = 'user/private.html'

class LocationView(TemplateView):
    template_name = 'user/location.html'


#login template
class HomeView(TemplateView):
    template_name = 'home.html'
"""
class UserCreateView(CreateView): 
    template_name = 'registration/register.html' 
    form_class = UserCreationForm 
    success_url = reverse_lazy('register_done') 
"""
class UserCreateView(VerifyEmailMixin, CreateView):
    model = get_user_model()
    form_class = UserCreationForm
    #template_name = 'registration/register.html'
    success_url = '/accounts/register/done/'
    verify_url = '/accounts/verify/'
    #success_url = reverse_lazy('register_done')
    email_template_name = 'registration/registration_verification.html'
    token_generator = default_token_generator

    def form_valid(self, form):
        response = super().form_valid(form)
        if form.instance:
            self.send_verification_email(form.instance)
        return response

    def send_verification_email(self, user):
        token = self.token_generator.make_token(user)
        url = self.build_verification_link(user, token)
        subject = '나만의 작은 카페 회원가입 인증메일입니다.'
        message = 'Please go to the following address to authenticate. {}'.format(url)
        html_message = render(self.request, self.email_template_name, {'url': url}).content.decode('utf-8')
        user.email_user(subject, message, settings.EMAIL_HOST_USER, html_message=html_message)
        messages.info(self.request, 'Congratulations on your registration.!!! We sent the authentication email to the email address you signed up for, so please confirm after checking it.')

    

    def build_verification_link(self, user, token):
        return '{}/user/{}/verify/{}/'.format(self.request.META.get('HTTP_ORIGIN'), user.pk, token)

#email authentication
from django.http import HttpResponseRedirect
from django.views.generic.base import TemplateView

class UserVerificationView(TemplateView):
    model = get_user_model()
    redirect_url = '/accounts/register/done/'
    token_generator = default_token_generator
    

    def get(self, request, *args, **kwargs):
        if self.is_valid_token(**kwargs):
            messages.info(request, 'success.')
        else:
            messages.error(request, 'failed.')
        return HttpResponseRedirect(self.redirect_url)

    def is_valid_token(self, **kwargs):
        pk = kwargs.get('pk')
        token = kwargs.get('token')
        user = self.model.objects.get(pk=pk)
        is_valid = self.token_generator.check_token(user, token)
        if is_valid:
            user.is_active = True
            user.save()     
        return is_valid

class ResendVerifyEmailView(VerifyEmailMixin, FormView):
    model = get_user_model()
    form_class = VerificationEmailForm
    success_url = '/accounts/register/done/'
    template_name = 'registration/resend_verify_email.html'
    email_template_name = 'registration/registration_verification.html'
    token_generator = default_token_generator

    def send_verification_email(self, user):
        token = self.token_generator.make_token(user)
        url = self.build_verification_link(user, token)
        subject = 'Congratulations on your registration.!!!'
        message = 'Please go to the following address to authenticate. {}'.format(url)
        html_message = render(self.request, self.email_template_name, {'url': url}).content.decode('utf-8')
        user.email_user(subject, message, from_email=settings.EMAIL_HOST_USER, html_message=html_message)
        messages.info(self.request, 'Congratulations on your registration.!!! We sent the authentication email to the email address you signed up for, so please confirm after checking it.')
    
    def build_verification_link(self, user, token):
        return '{}/user/{}/verify/{}/'.format(self.request.META.get('HTTP_ORIGIN'), user.pk, token)

    def form_valid(self, form):
        email = form.cleaned_data['email']
        try:
            user = self.model.objects.get(email=email)
        except self.model.DoesNotExist:
            messages.error(self.request, 'Unknown user.')
        else:
            self.send_verification_email(user)
        return super().form_valid(form)

from django.contrib.auth import get_user_model
from django.core.exceptions import ValidationError

"""
class RegisteredEmailValidator:
    user_model = get_user_model()
    code = 'invalid'

    def __call__(self, email):
        try:
            user = self.user_model.objects.get(email=email)
        except self.user_model.DoesNotExist:
            raise ValidationError('Email not subscribed.', code=self.code)
        else:
            if user.is_active:
                raise ValidationError('You are already authenticated.', code=self.code)

        return
"""



class UserCreateDoneTV(TemplateView):
    template_name = 'registration/register_done.html'

###############################################################################

class UserCreateView1(VerifyEmailMixin, CreateView):
    model = get_user_model()
    form_class = UserCreationForm
    template_name = 'registration/registerm.html'
    success_url = '/accounts/registerm/done/'
    verify_url = '/accounts/verify/'
    #success_url = reverse_lazy('register_done')
    email_template_name = 'registration/registration_verification.html'
    token_generator = default_token_generator

    def form_valid(self, form):
        response = super().form_valid(form)
        if form.instance:
            self.send_verification_email(form.instance)
        return response

    def send_verification_email(self, user):
        token = self.token_generator.make_token(user)
        url = self.build_verification_link(user, token)
        subject = '나만의 작은 카페 회원가입 인증메일입니다.!!!'
        message = 'Please go to the following address to authenticate. {}'.format(url)
        html_message = render(self.request, self.email_template_name, {'url': url}).content.decode('utf-8')
        user.email_user(subject, message, settings.EMAIL_HOST_USER, html_message=html_message)
        messages.info(self.request, 'Congratulations on your registration.!!! We sent the authentication email to the email address you signed up for, so please confirm after checking it.')

    def build_verification_link(self, user, token):
        return '{}/manager/{}/verify/{}/'.format(self.request.META.get('HTTP_ORIGIN'), user.pk, token)

#email authentication

class UserVerificationView1(TemplateView):
    model = get_user_model()
    redirect_url = '/accounts/registerm/done/'
    token_generator = default_token_generator

    def get(self, request, *args, **kwargs):
        if self.is_valid_token(**kwargs):
            messages.info(request, 'success.')
        else:
            messages.error(request, 'failed.')
        return HttpResponseRedirect(self.redirect_url)

    def is_valid_token(self, **kwargs):
        pk = kwargs.get('pk')
        token = kwargs.get('token')
        user = self.model.objects.get(pk=pk)
        is_valid = self.token_generator.check_token(user, token)
        if is_valid:
            user.is_wait = True
            user.save()     
        return is_valid

class ResendVerifyEmailView1(VerifyEmailMixin, FormView):
    model = get_user_model()
    form_class = VerificationEmailForm
    success_url = '/accounts/registerm/done/'
    template_name = 'registration/resend_verify_email.html'
    email_template_name = 'registration/registration_verification.html'
    token_generator = default_token_generator

    def send_verification_email(self, user):
        token = self.token_generator.make_token(user)
        url = self.build_verification_link(user, token)
        subject = 'Congratulations on your registration.!!!'
        message = 'Please go to the following address to authenticate. {}'.format(url)
        html_message = render(self.request, self.email_template_name, {'url': url}).content.decode('utf-8')
        user.email_user(subject, message, from_email=settings.EMAIL_HOST_USER, html_message=html_message)
        messages.info(self.request, 'Congratulations on your registration.!!! We sent the authentication email to the email address you signed up for, so please confirm after checking it.')
    
    def build_verification_link(self, user, token):
        return '{}/manager/{}/verify/{}/'.format(self.request.META.get('HTTP_ORIGIN'), user.pk, token)

    def form_valid(self, form):
        email = form.cleaned_data['email']
        try:
            user = self.model.objects.get(email=email)
        except self.model.DoesNotExist:
            messages.error(self.request, 'Unknown user.')
        else:
            self.send_verification_email(user)
        return super().form_valid(form)

class UserCreateDoneTV1(TemplateView):
    template_name = 'registration/register_done.html'

########################################################################################

def password_reset_request(request):
	if request.method == "POST":
		password_reset_form = PasswordResetForm(request.POST)
		if password_reset_form.is_valid():
			data = password_reset_form.cleaned_data['email']
			associated_users = User.objects.filter(Q(email=data))
			if associated_users.exists():
				for user in associated_users:
					subject = "Password Reset Requested"
					email_template_name = "main/password/password_reset_email.txt"
					c = {
					"email":user.email,
					'domain':'127.0.0.1:8000',
					'site_name': 'Website',
					"uid": urlsafe_base64_encode(force_bytes(user.pk)),
					"user": user,
					'token': default_token_generator.make_token(user),
					'protocol': 'http',
					}
					email = render_to_string(email_template_name, c)
					try:
						send_mail(subject, email, 'admin@example.com' , [user.email], fail_silently=False)
					except BadHeaderError:
						return HttpResponse('Invalid header found.')
					return redirect ("/password_reset/done/")
	password_reset_form = PasswordResetForm()
	return render(request=request, template_name="main/password/password_reset.html", context={"password_reset_form":password_reset_form})

###################################################################################################

#related login
@csrf_exempt
def app_login(request):
    if request.method == 'POST':
        print("request log" + str(request.body))
        id = request.POST.get('userid','')
        pw = request.POST.get('userpw','')
        print("id = " + id + " pw = " + pw)
        result = authenticate(username = id, password = pw)

        if result:
            print("login!")
            #user = request.session['id']
            query_set=User.objects.filter(email=id).values_list('id', flat=True)
            query_set1=User.objects.filter(email=id).values_list('nickname', flat=True)
            query_set2=User.objects.filter(email=id).values_list('phone', flat=True)

            query_set3=User.objects.filter(email=id).values_list('r1', flat=True)
            query_set4=User.objects.filter(email=id).values_list('r2', flat=True)
            query_set5=User.objects.filter(email=id).values_list('r3', flat=True)
            query_set6=User.objects.filter(email=id).values_list('r4', flat=True)
            query_set7=User.objects.filter(email=id).values_list('r5', flat=True)
            query_set8=User.objects.filter(email=id).values_list('r6', flat=True)

            serializer = UserSerializer(query_set, many = True)
            #query_set = list(query_set.values())

############ dirty code
            a = list(query_set.values_list('id', flat=True))
            nickname=list(query_set1.values_list('nickname', flat=True))
            phone=list(query_set2.values_list('phone', flat=True))

            r1=list(query_set3.values_list('r1', flat=True))
            r2=list(query_set3.values_list('r2', flat=True))
            r3=list(query_set3.values_list('r3', flat=True))
            r4=list(query_set3.values_list('r4', flat=True))
            r5=list(query_set3.values_list('r5', flat=True))
            r6=list(query_set3.values_list('r6', flat=True))
            
            b = a[0]
#int형은 문자로 바꿔야 함.
            b = str(b)
            nn=nickname[0]
            p=phone[0]

            rr = []
            rr1=r1[0]
            rr2=r2[0]
            rr3=r3[0]
            rr4=r4[0]
            rr5=r5[0]
            rr6=r6[0]
            rr.append(str(rr1))
            rr.append(str(rr2))
            rr.append(str(rr3))
            rr.append(str(rr4))
            rr.append(str(rr5))
            rr.append(str(rr6))

###################################################################################
            result=b+','+nn+','+p
            for item in rr:
                if (item != "None"):
                    result = result + ',' + item
            print(result)
            return JsonResponse({'code':'0000','msg':result},status=200)
        #return JsonResponse({'code':'0000','msg':'login'},status=200)
        else:
            print("failed")
            return JsonResponse({'code':'1001','msg':'failed'},status=200)

###################################################################################

@csrf_exempt
def app_loginm(request):
    if request.method == 'POST':
        print("request log" + str(request.body))
        id = request.POST.get('userid','')
        pw = request.POST.get('userpw','')
        print("id = " + id + " pw = " + pw)
        result = authenticate(username = id, password = pw)

        if result:
            print("login!")
            #user = request.session['id']
            query_set=User.objects.filter(email=id).values_list('cno', flat=True)
            a = list(query_set.values_list('cno', flat=True))
            b=a[0]

            result=str(b)
            print(result)
            return JsonResponse({'code':'0000','msg':result},status=200)
        #return JsonResponse({'code':'0000','msg':'login'},status=200)
        else:
            print("failed")
            return JsonResponse({'code':'1001','msg':'failed'},status=200)
#######################################################################################

import datetime
@api_view(['GET'])
def get_time(request):
    now = datetime.datetime.now()
    return HttpResponse(now)

#related login
@csrf_exempt
def reservation(request):
    if request.method == 'POST':
        print("request log" + str(request.body))
        st = request.POST.get('time','')
        uid = request.POST.get('uid','')
        now = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,datetime.datetime.now().hour,datetime.datetime.now().minute,datetime.datetime.now().second)
        print(now)
        ad=st
        print(st)
        st = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,int(st[0:2]),int(st[3:5]),int(st[6:8]))
        if now >= datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,22,0,0):
            if now < datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,23,59,59):
                if st < datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,22,0,0):
                    if st < datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,2,0,0):
                        daynow = datetime.datetime.now()+datetime.timedelta(days=1)
                        monthnow = datetime.datetime.now()+datetime.timedelta(days=1)
                        yearnow = datetime.datetime.now()+datetime.timedelta(days=1)
                        print(daynow)
                        st = datetime.datetime(yearnow.year,monthnow.month,daynow.day,int(ad[0:2]),int(ad[3:5]),int(ad[6:8]))    
        else:
    	    st = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,int(ad[0:2]),int(ad[3:5]),int(ad[6:8]))

        print(st)
        print("=" * 30)

        if uid!=0:
            pt = now + datetime.timedelta(hours=2)
            print(st)
            if st <= pt:
                 if now < st:
                     result = "True"
                 else:
                     result = "Gone."
            elif st > pt:
                result = "False"
            return JsonResponse({'code':'0000','msg':result},status=200)

        elif uid==0:
            pt = now + datetime.timedelta(minutes=30)
            print(st)
            print(pt)
            if st <= pt:
                if now < st:
                    result = "True"
                else:
                    result = "gone."
            elif st > pt:
                result = "False"
            print(result)
            return JsonResponse({'code':'0000','msg':result},status=200)
    #return JsonResponse({'code':'0000','msg':result},status=200)

#################################################################################################
#OpenClose Calculator

@csrf_exempt
def openclose(request):
    if request.method == 'GET':
        weekday = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun']
        query_set=Cafe.objects.all().values_list('id', 'start_time','end_time','mon','tue','wed','thu','fri','sat','sun','close')
        now = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,datetime.datetime.now().hour,datetime.datetime.now().minute,datetime.datetime.now().second)
        serializer = CafeSerializer(query_set, many = True)
        a=query_set
        j=0
        print("=" * 50)
        print(" " * 15 + "오픈 클로즈 시간 계산중")
        print("=" * 50)
        today = datetime.datetime.today().weekday()
        #요일별로 열리게 하기 위해 오늘의 날짜를 저장하는 곳
        
        

        for i in query_set:
            if a[j][3+today] == True :
                start = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,a[j][1].hour,a[j][1].minute,a[j][1].second)
                end = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,a[j][2].hour,a[j][2].minute,a[j][2].second)
                print(i[10])

                if start < end:
                    if now > end:
                        post_instance = Cafe.objects.get(id=a[j][0])
                        post_instance.close= 'False'
                        post_instance.save()
                if start > end:
                    if now > start:
                        post_instance = Cafe.objects.get(id=a[j][0])
                        post_instance.close= 'False'
                        post_instance.save()

                if start < end:
                    if now >= start:
                        if i[10]==False:
                            if now < end:
                                post_instance = Cafe.objects.get(id=a[j][0])
                                post_instance.business = 'True'
                                post_instance.save()
                        if now > end:
                            post_instance = Cafe.objects.get(id=a[j][0])
                            post_instance.business = 'False'
                            post_instance.close= 'False'
                            post_instance.save()

                elif start > end:
                    if now >= start:
                        if i[10]==False:
                            if now > end:
                                post_instance = Cafe.objects.get(id=a[j][0])
                                post_instance.business = 'True'
                                post_instance.save()
                        if now < end:
                            post_instance = Cafe.objects.get(id=a[j][0])
                            post_instance.business = 'False'
                            post_instance.close='False'
                            post_instance.save()
                    elif now < start:
                        #if now < end:
                            #post_instance = Cafe.objects.get(id=a[j][0])
                            #post_instance.business = 'True'
                            #post_instance.save()
                        if now > end:
                            post_instance = Cafe.objects.get(id=a[j][0])
                            post_instance.business = 'False'
                            post_instance.close='False'
                            post_instance.save()

                else:
                    post_instance = Cafe.objects.get(id=a[j][0])
                    post_instance.business = 'False'
                    post_instance.save()
            else:
                start = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,a[j][1].hour,a[j][1].minute,a[j][1].second)
                end = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,a[j][2].hour,a[j][2].minute,a[j][2].second)
                if start < end:
                    if now >= start:
                        if now > end:
                            post_instance = Cafe.objects.get(id=a[j][0])
                            post_instance.business = 'False'
                            post_instance.close='False'
                            post_instance.save()
                elif start > end:
                    if now >= start:
                        if now < end:
                            post_instance = Cafe.objects.get(id=a[j][0])
                            post_instance.business = 'False'
                            post_instance.close='False'
                            post_instance.save()
                    elif now < start:
                        if now > end:
                            post_instance = Cafe.objects.get(id=a[j][0])
                            post_instance.business = 'False'
                            post_instance.close='False'
                            post_instance.save()
            j=j+1
    print("오픈 클로즈 시간계산 종료")
    return HttpResponse()    

#################################################################################################
#Star Calculator

@csrf_exempt
def star(request):
    if request.method == 'GET':
        query_set=Cafe.objects.all().values_list('id','star')
        serializer = CafeSerializer(query_set, many = True)
        j=0
        k=0
        count=0
        sum=0
        result = 0
        print("=" * 50)
        print(" " * 11 + "댓글 별점 계산중입니다~~~")
        print("=" * 50)
        for i in query_set:
            query_setr = Review.objects.filter(cno=query_set[j][0]).values_list('cno','star')
            for plus in query_setr:
                sum = sum + query_setr[k][1]
                count = count + 1
                k = k + 1
            if count >= 1:
                result = sum /count            
            post_instance = Cafe.objects.get(id=query_set[j][0])
            post_instance.star = result
            post_instance.save()
            result=0
            sum=0
            k=0
            count=0
            j = j + 1

    print("댓글 계산 종료")
    return HttpResponse()

 
#####################################################################################################
#tag automation

@csrf_exempt
def tag(request):
    if request.method == 'GET':
#리뷰 데이터 cafe넘버와 tag가져옴
        query_set=Review.objects.all().values_list('cno','tag')
#카페 테이블에서 id와 id 가져옴.
        cafequery_set=Cafe.objects.all().values_list('id','id')
        print("=" * 50)
        print(" " * 11 + "태그 기록중입니다~~~")
        print("=" * 50)

        for i in range(0,len(cafequery_set)):
            j=0
            
#카페 id가 있는 것만 불러옴.
            query_setr = Review.objects.filter(cno=cafequery_set[i][0]).values_list('cno','tag')
# 요것은 카페별 댓글 태그 숫자

            tag=[]
            count_list=[]

            for z in range(0,len(query_setr)):             
                tag.append(query_setr[z][1])
            tag_set = set(tag)
            tag_list= list(tag_set)
            for z in range(0,len(tag_list)):             
                count_list.append(0)

            for z in range(0,len(query_setr)):  
                for k in range(0,len(tag_list)):
                    if tag_list[k] == query_setr[z][1]:
                        count_list[k]=count_list[k]+1
            if not count_list:
                first = 0
            else:
                first = max(count_list)
            second = 0
            for z in range(0,len(count_list)):
                if (second < count_list[z]) and (count_list[z] < first):
                    second = count_list[z]

            #print(tag_list[count_list.index(first)])
            #print(tag_list[count_list.index(second)])

            if first != 0:
                post_instance = Cafe.objects.get(id=cafequery_set[i][0])
                post_instance.tag1 = tag_list[count_list.index(first)]
                post_instance.save()
            else:
                post_instance = Cafe.objects.get(id=cafequery_set[i][0])
                post_instance.tag1 = "아직 없음"
                post_instance.save()

            if second != 0:
                post_instance = Cafe.objects.get(id=cafequery_set[i][0])
                post_instance.tag2 = tag_list[count_list.index(second)]
                post_instance.save()
            else:
                post_instance = Cafe.objects.get(id=cafequery_set[i][0])
                post_instance.tag2 = "아직 없음"
                post_instance.save()
        print("=" * 50)
        print(" " * 11 + "태그 기록 종료~~~")
        print("=" * 50)

    return HttpResponse()

###################################################################################################################################
#age automation

@csrf_exempt
def age_stats(request):
    if request.method == 'GET':
        now = datetime.datetime(datetime.datetime.now().year,datetime.datetime.now().month,datetime.datetime.now().day,datetime.datetime.now().hour,datetime.datetime.now().minute,datetime.datetime.now().second)
        now = str(now)
        now = now[:4]
        now = int(now)
        print(now)
        query_set1=Cafe.objects.all().values_list('id', 'cafe_name')   
        cno=[]
        for i in query_set1:
            cno.append(i[0])
        #print(cno)

        for i in cno:
            query_set=Check.objects.filter(cno=i).values_list('uid', flat=True)
            temp= list(query_set.values_list('uid', flat=True))
            uid=temp
            age10=0
            age20=0
            age30=0
            age40=0
            age50=0
            age60=0
            if len(uid) !=0:
                for j in uid:
                    query_set2=User.objects.filter(id=j).values_list('birth_day', flat=True)
                    temp1= list(query_set2.values_list('birth_day', flat=True))
                    year=str(temp1[0])
                    year=year[:4]
                    year=int(year)
                    age=now-year+1
                    if (age>=10) and (age<=19):
                        age10=age10+1
                    if (age>=20) and (age<=29):
                        age20=age20+1
                    if (age>=30) and (age<=39):
                        age30=age30+1
                    if (age>=40) and (age<=49):
                        age40=age40+1
                    if (age>=50) and (age<=59):
                        age50=age50+1
                    if (age>=60):
                        age60=age60+1
                post_instance = Age.objects.get(cno=i)
                post_instance.age10 = age10
                post_instance.age20 = age20
                post_instance.age30 = age30
                post_instance.age40 = age40
                post_instance.age50 = age50
                post_instance.over10 = age60     
                post_instance.save()

    return HttpResponse()

###################################################################################################################################
#Gender automation

@csrf_exempt
def gender_stats(request):
    if request.method == 'GET':
        query_set1=Cafe.objects.all().values_list('id', 'cafe_name')   
        cno=[]
        for i in query_set1:
            cno.append(i[0])

        for i in cno:
            query_set=Check.objects.filter(cno=i).values_list('uid', flat=True)
            temp= list(query_set.values_list('uid', flat=True))
            uid=temp
            man=0
            woman=0
            if len(uid) !=0:
                for j in uid:
                    query_set2=User.objects.filter(id=j).values_list('gender', flat=True)
                    temp1 = list(query_set2.values_list('gender', flat=True))
                    gender= temp1[0]
                    if gender=="남자":
                        man=man+1
                    elif gender=="여자":
                        woman=woman+1
                post_instance = Gender.objects.get(cno=i)
                post_instance.man = man
                post_instance.woman = woman
                post_instance.save()

    return HttpResponse()


###################################################################################################################################
#Collaborative Filtering Item-based recommendation

# How to write?
# 먼저 카페별로 이 카페를 방문한 사람은 어떤 카페를 방문했는지 모으고 
# cosine similarity가 높은 카페 추천
# x는 카페 y는 user

@csrf_exempt
def recommendation(request):
    if request.method == 'GET':
#uid 리스트 생성
        query_set=User.objects.all().values_list('id', 'name')
        uid=[]
        for i in query_set:
            uid.append(i[0])
        print(uid)

#cno 리스트
        query_set1=Cafe.objects.all().values_list('id', 'cafe_name')   
        cno=[]
        for i in query_set1:
            cno.append(i[0])
        print(cno)
        
#예약된 유저번호 and 예약된 카페번호
        checkcno=[]
        checkuid=[]
        query_set2=Check.objects.all().values_list('uid', 'cno')
        for i in query_set2:
            checkcno.append(i[1])
        for i in query_set2:
            checkuid.append(i[0])
        #print(checkcno)
        #print(checkuid)

#matrix
        matrix=[]
        for i in range(len(cno)):
            line = []
            for j in range(len(uid)):
                line.append(0)     # 안쪽 리스트에 0 추가
            matrix.append(line)         # 전체 리스트에 안쪽 리스트를 추가

        x=0

        for i in cno:
            y=0
            for j in uid:
                z=0
                for q in checkuid:
                    if(checkuid[z]==j) and (checkcno[z]==i):
                        matrix[x][y]=matrix[x][y]+1
                    z=z+1
                y=y+1
            x=x+1
        print(matrix)
        
# Cosine Similarity 적용
# x,y는 martrix control을 위한 변수
# 
        pick=0 # 고정 픽 
        x=0
        y=0
        up=0
        down1=0
        down2=0
        cosines = []
        mx=0
        my=0
        recommendation = []
        for i in cno:
                y=0
                for j in uid:
                    if matrix[x][y]>=1: 
                        mx=0    # mx my변수는 수식을 구하기 위한 변수
                        for k in cno:
                            my=0
                            for z in uid:
# 동일한 카페도 추천 받으려면 바로 밑 if문에 not()빼면 됨.
                                if (mx < len(cno)) and not(matrix[mx][y]>=1):
                                    up = up + (matrix[x][my] * matrix[mx][my])
                                    down1 = down1 + (matrix[x][my] * matrix[x][my])
                                    down2 = down2+(matrix[mx][my] * matrix[mx][my])
                                    my = my+1
                            try:
                                cosines.append(round(up/(math.sqrt(down1)*math.sqrt(down2)),2))
                            except:    
                                cosines.append(0)
                            up=0
                            down1=0
                            down2=0
                            mx = mx + 1
                            
                        #cosines.sort(reverse=True)
                        print(cosines)
                        index = -1
                        for item in cosines:
                            index = index + 1
                            if float(item)>=0.80:
                                recommendation.append(cno[index])
                        if len(recommendation)>=1:
                            print(str(uid[y]) + str(recommendation))
                            print("-" * 50)


                        post_instance = User.objects.get(id=uid[y])
                        if len(recommendation) >= 1:
                            post_instance.r1 = recommendation[0]
                        if len(recommendation) >= 2:
                            post_instance.r2 = recommendation[1]
                        if len(recommendation) >= 3:
                            post_instance.r3 = recommendation[2]
                        if len(recommendation) >= 4:
                            post_instance.r4 = recommendation[3]
                        if len(recommendation) >= 5:
                            post_instance.r5 = recommendation[4]
                        if len(recommendation) >= 6:
                            post_instance.r6 = recommendation[5]
                        post_instance.save()
                        del recommendation[:]
                        #print(cosines)
                        del cosines[:]
                    y = y + 1
                x = x + 1

    return HttpResponse()

























