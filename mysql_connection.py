import mysql.connector

def get_connection() :
    connection = mysql.connector.connect(
            host = 'yh.cpos3ccatavx.ap-northeast-2.rds.amazonaws.com',
            database = 'TeamUzoo',
            user = 'team_uzoo_user',
            password = 'gkswogns12')
    
    return connection