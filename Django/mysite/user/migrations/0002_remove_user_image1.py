# Generated by Django 3.1 on 2020-09-08 09:41

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('user', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='user',
            name='image1',
        ),
    ]
