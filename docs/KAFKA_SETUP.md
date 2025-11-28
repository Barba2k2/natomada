# Kafka & Event-Driven Architecture - NaTomada

## 📋 Resumo

Este documento explica a implementação completa de **Event-Driven Architecture** usando **Apache Kafka** na aplicação NaTomada. O sistema publica eventos de negócio que podem ser consumidos assincronamente para processamento, analytics, notificações e integrações.

---

## 🎯 O que Foi Implementado

### 1. **Eventos de Domínio**

Eventos são criados quando ações importantes acontecem no sistema:

#### **Autenticação** (`natomada.auth.events`)
- `USER_REGISTERED` - Novo usuário se registrou
- `USER_LOGGED_IN` - Usuário fez login
- `USER_LOGGED_OUT` - Usuário fez logout

#### **Veículos** (`natomada.vehicles.events`)
- `VEHICLE_ADDED` - Usuário adicionou um veículo
- `VEHICLE_REMOVED` - Usuário removeu um veículo
- `VEHICLE_IMAGE_UPLOADED` - Foto do veículo foi enviada

#### **Estações** (`natomada.stations.events`)
- `STATION_FAVORITED` - Usuário favoritou uma estação
- `STATION_UNFAVORITED` - Usuário removeu favorito

---

## 📁 Estrutura de Arquivos Criados

```
src/main/java/com/barbatech/natomada/
├── infrastructure/
│   ├── events/
│   │   ├── BaseEvent.java                    # Classe base para todos eventos
│   │   ├── auth/
│   │   │   ├── UserRegisteredEvent.java
│   │   │   ├── UserLoggedInEvent.java
│   │   │   └── UserLoggedOutEvent.java
│   │   ├── cars/
│   │   │   ├── VehicleAddedEvent.java
│   │   │   ├── VehicleRemovedEvent.java
│   │   │   └── VehicleImageUploadedEvent.java
│   │   └── stations/
│   │       ├── StationFavoritedEvent.java
│   │       └── StationUnfavoritedEvent.java
│   │
│   ├── config/
│   │   ├── KafkaTopicConfig.java             # Configuração de tópicos
│   │   ├── KafkaProducerConfig.java          # Configuração do produtor
│   │   ├── KafkaConsumerConfig.java          # Configuração do consumidor
│   │   └── JacksonConfig.java                # Serialização JSON
│   │
│   ├── kafka/
│   │   ├── EventPublisher.java               # Serviço para publicar eventos
│   │   └── listeners/
│   │       ├── AuthEventsListener.java       # Processa eventos de auth
│   │       ├── VehicleEventsListener.java    # Processa eventos de veículos
│   │       └── StationEventsListener.java    # Processa eventos de estações
│   │
src/main/resources/
└── logback-spring.xml                         # Configuração avançada de logs
```

---

## 🔧 Como Funciona

### **1. Publicação de Eventos**

Quando algo importante acontece, o serviço publica um evento:

```java
// Exemplo: AuthService.java linha 72-79
UserRegisteredEvent event = UserRegisteredEvent.of(
    user.getId(),
    user.getName(),
    user.getEmail(),
    user.getPhone()
);
eventPublisher.publish("natomada.auth.events", event);
```

### **2. Consumo de Eventos**

Listeners processam eventos assincronamente:

```java
// Exemplo: AuthEventsListener.java
@KafkaListener(topics = "natomada.auth.events")
public void handleAuthEvent(...) {
    // Processar evento
    // - Enviar email de boas-vindas
    // - Criar perfil de analytics
    // - Notificar admin
}
```

---

## 🌊 Fluxo Completo de um Evento

```
┌──────────────┐
│ 1. Usuário   │
│    se        │
│   registra   │
└──────┬───────┘
       │
       ▼
┌──────────────────────────┐
│ 2. AuthService           │
│    - Salva usuário       │
│    - Publica evento      │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ 3. Kafka Topic           │
│    natomada.auth.events  │
└──────┬───────────────────┘
       │
       ├──────────────────────┐
       │                      │
       ▼                      ▼
┌────────────────┐   ┌──────────────────┐
│ 4a. Listener   │   │ 4b. Analytics    │
│     Email      │   │     Service      │
│  📧 Envia      │   │  📊 Registra     │
│   boas-vindas  │   │     no painel    │
└────────────────┘   └──────────────────┘
```

---

## 🚀 Como Testar

### **1. Acesse o Kafka UI**

```
http://localhost:8090
```

Você verá os tópicos criados:
- `natomada.auth.events`
- `natomada.vehicles.events`
- `natomada.stations.events`
- `natomada.dlq` (Dead Letter Queue)

### **2. Registre um Novo Usuário**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@teste.com",
    "phone": "11999999999",
    "password": "senha123",
    "passwordConfirmation": "senha123"
  }'
```

### **3. Verifique os Logs**

```bash
docker compose logs app -f | grep "Publishing event"
```

Você verá:
```
Publishing event to topic 'natomada.auth.events': eventType=USER_REGISTERED
```

### **4. Verifique no Kafka UI**

- Acesse http://localhost:8090
- Clique em `natomada.auth.events`
- Vá em **Messages**
- Você verá o evento JSON completo!

---

## 📊 Logback - Sistema de Logs

### **Configuração Avançada**

O arquivo `logback-spring.xml` configura:

1. **Console Output** - Logs coloridos no terminal
2. **File Output** - Logs salvos em arquivo rotativo
3. **Kafka Events Log** - Log separado para eventos Kafka
4. **Profiles** - Comportamento diferente por ambiente

### **Logs por Ambiente**

```bash
# Development (mais verboso)
SPRING_PROFILES_ACTIVE=dev

# Docker (apenas console)
SPRING_PROFILES_ACTIVE=docker

# Production (menos verboso, apenas warnings)
SPRING_PROFILES_ACTIVE=prod
```

### **Exemplo de Log**

```
2025-11-01 01:32:10 [main] INFO  c.b.n.a.a.s.AuthService - User registered successfully with ID: 123
2025-11-01 01:32:10 [main] INFO  c.b.n.i.k.EventPublisher - Publishing event to topic 'natomada.auth.events'
2025-11-01 01:32:10 [kafka-producer] INFO  c.b.n.i.k.EventPublisher - Event published successfully: offset=42
2025-11-01 01:32:11 [kafka-consumer] INFO  c.b.n.i.k.l.AuthEventsListener - Processing USER_REGISTERED: userId=123
2025-11-01 01:32:11 [kafka-consumer] INFO  c.b.n.i.k.l.AuthEventsListener - 📧 Sending welcome email to: joao@teste.com
```

---

## 🎓 Casos de Uso Reais

### **1. Email de Boas-Vindas**
Quando `USER_REGISTERED` → Envia email automático

### **2. Analytics**
Todos eventos → Dashboard de métricas em tempo real

### **3. Recomendações**
`VEHICLE_ADDED` → Encontra estações compatíveis

### **4. Notificações Push**
`STATION_FAVORITED` → Notifica sobre atualizações da estação

### **5. Auditoria**
Todos eventos → Log permanente de ações do usuário

---

## ⚙️ Configurações Importantes

### **Kafka Producer** (KafkaProducerConfig.java)
- **Compression**: Snappy (reduz tamanho)
- **Idempotence**: Previne duplicatas
- **Retries**: Até 3 tentativas
- **Acks**: Espera confirmação do leader

### **Kafka Consumer** (KafkaConsumerConfig.java)
- **Manual Acknowledgment**: Controle fino de processamento
- **Error Handling**: Não perde mensagens em erro
- **Concurrency**: 3 threads por consumer

### **Tópicos** (KafkaTopicConfig.java)
- **Partitions**: 3 (paralelismo)
- **Replication**: 1 (dev/single broker)
- **Compaction**: Habilitado (eficiência)

---

## 🔍 Troubleshooting

### **Evento não aparece no Kafka UI**

```bash
# Verificar se tópico foi criado
docker compose exec kafka kafka-topics --list --bootstrap-server localhost:9092

# Verificar se mensagem foi enviada
docker compose logs app | grep "Event published successfully"
```

### **Consumer não está processando**

```bash
# Verificar consumer groups
docker compose logs app | grep "KafkaListener"

# Forçar restart
docker compose restart app
```

### **Performance lenta**

Ajuste o `batch.size` e `linger.ms` em `KafkaProducerConfig.java`

---

## 📚 Próximos Passos

### **Para Produção:**

1. **Implementar Dead Letter Queue (DLQ)** ✅ (já configurado)
2. **Adicionar retry policy** com backoff exponencial
3. **Monitoramento** com Prometheus + Grafana
4. **Alertas** para falhas críticas
5. **Backup de eventos** para auditoria
6. **Implementar os TODOs** nos listeners:
   - Envio real de emails
   - Integração com analytics
   - Notificações push
   - Geração de relatórios

### **Melhorias Futuras:**

- **Event Sourcing**: Reconstruir estado a partir de eventos
- **CQRS**: Separar leitura e escrita
- **Saga Pattern**: Transações distribuídas
- **Stream Processing**: Kafka Streams para agregações

---

## 🎉 Conclusão

Você agora tem um sistema completo de **Event-Driven Architecture**!

✅ Eventos de negócio estruturados
✅ Kafka configurado e rodando
✅ Producers publicando eventos
✅ Consumers processando assincronamente
✅ Logs organizados com Logback
✅ Kafka UI para monitoramento

**Tudo pronto para escalar e adicionar novas funcionalidades!**
