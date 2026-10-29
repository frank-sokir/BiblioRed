import urllib.request,urllib.error,urllib.parse,http.cookiejar,json,datetime,zipfile,io
BASE='http://127.0.0.1:8081/api'
class Client:
 def __init__(self):self.op=urllib.request.build_opener(urllib.request.HTTPCookieProcessor(http.cookiejar.CookieJar()));self.token()
 def token(self):self.csrf=self.call('/csrf')
 def call(self,path,method='GET',body=None,form=False):
  hdr={};data=None
  if method!='GET':hdr[self.csrf['headerName']]=self.csrf['token']
  if body is not None: data=urllib.parse.urlencode(body).encode() if form else json.dumps(body).encode();hdr['Content-Type']='application/x-www-form-urlencoded' if form else 'application/json'
  with self.op.open(urllib.request.Request(BASE+path,data=data,headers=hdr,method=method)) as r:
   raw=r.read();return json.loads(raw) if raw and 'application/json' in r.headers.get('Content-Type','') else raw
 def login(self,correo):self.call('/login','POST',{'username':correo,'password':'BiblioRed2026!'},True);self.token()
r=Client();r.login('lector@bibliored.local');assert r.call('/yo')['rol']=='USUARIO'
cat=r.call('/catalogo');assert len(cat)==6;assert sum(len(x['ejemplares']) for x in cat)==18
p=r.call('/prestamos','POST',{'ejemplarId':1,'fechaRecojo':str(datetime.date.today())});assert p['estado']=='SOLICITADO'
a=Client();a.login('admin@bibliored.local');p=a.call(f"/personal/prestamos/{p['id']}/entregar",'POST');assert p['estado']=='ACTIVO'
p=a.call(f"/personal/prestamos/{p['id']}/devolver",'POST',{'observacion':'Prueba de integración MySQL'});assert p['estado']=='DEVUELTO'
assert r.call('/prestamos')[0]['estado']=='DEVUELTO'
raw=a.call('/personal/reportes/inventario.xlsx');assert zipfile.is_zipfile(io.BytesIO(raw))
assert r.call('/catalogo')[0]['ejemplares'][0]['estado']=='DISPONIBLE'
try:r.call('/admin/usuarios');raise AssertionError('Permiso incorrecto')
except urllib.error.HTTPError as e:assert e.code==403
print('MYSQL INTEGRATION PASS: SQL importado, login BCrypt, 6 títulos, 18 ejemplares, solicitud, entrega, devolución, disponibilidad, inventario XLSX y permisos.')
