# Generated by Django 3.1 on 2020-09-17 13:57

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('backend', '0003_cafe_event'),
    ]

    operations = [
        migrations.AddField(
            model_name='booking1',
            name='uid',
            field=models.IntegerField(blank=True, default=0),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='booking2',
            name='uid',
            field=models.IntegerField(blank=True, default=1),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='booking3',
            name='uid',
            field=models.IntegerField(blank=True, default=0),
            preserve_default=False,
        ),
    ]
