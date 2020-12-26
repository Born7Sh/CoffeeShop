from django.views.generic import CreateView
from user.models import User
from user.forms import UserCreationForm
from .serilizers import FileTestSerializer

from django.views.decorators.csrf import csrf_exempt

@csrf_exempt
def contact(request):

class UserRegistrationView(CreateView):
    model = User                            
    fields = ('email', 'name', 'password')

from django.contrib.auth import get_user_model
from django.views.generic import CreateView

from user.forms import UserRegistrationForm


class UserRegistrationView(CreateView):
    model = get_user_model()
    form_class = UserRegistrationForm

class FileTestViewSet(ModelViewSet):
    queryset = FileTest.objects.all()
    serializer_class = FileTestSerializer