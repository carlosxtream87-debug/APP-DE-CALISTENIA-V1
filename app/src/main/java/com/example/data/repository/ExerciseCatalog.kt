package com.example.data.repository

import com.example.data.model.Difficulty
import com.example.data.model.Equipment
import com.example.data.model.Exercise
import com.example.data.model.MuscleGroup

object ExerciseCatalog {
    val exercises: List<Exercise> = listOf(
        // --- PEITORAL & TRÍCEPS (PUSH) ---
        Exercise(
            id = "pushup_standard",
            name = "Flexão Tradicional (Push-Up)",
            muscleGroup = MuscleGroup.PEITO,
            difficulty = Difficulty.INICIANTE,
            equipment = Equipment.NENHUM,
            description = "O exercício fundamental de empurrar na calistenia. Desenvolve força no peitoral, tríceps e deltoide anterior.",
            executionSteps = listOf(
                "Posicione as mãos no chão na largura dos ombros.",
                "Mantenha o corpo em linha reta (prancha) com glúteos e abdômen contraídos.",
                "Desça o peito até quase encostar no chão com os cotovelos a 45 graus do tronco.",
                "Empurre o chão com força até estender os braços completamente mantendo a protração escapular."
            ),
            tips = listOf(
                "Não deixe o quadril cair ou levantar demais.",
                "Evite cotovelos abertos em 90 graus para proteger os ombros.",
                "Foque em controlar a descida por 2 segundos."
            ),
            commonMistakes = listOf(
                "Amplitude curta sem descer o peito até o chão.",
                "Quadril frouxo gerando curvatura na lombar.",
                "Pescoço esticado para frente."
            ),
            isIsometric = false,
            defaultRepsOrSeconds = 12,
            defaultSets = 3,
            defaultRestSeconds = 60,
            regression = "Flexão com Joelhos Apoiados",
            progression = "Flexão Diamante ou Arqueiro"
        ),
        Exercise(
            id = "pushup_diamond",
            name = "Flexão Diamante",
            muscleGroup = MuscleGroup.BRACOS,
            difficulty = Difficulty.INTERMEDIARIO,
            equipment = Equipment.NENHUM,
            description = "Variação avançada com mãos unidas em formato de diamante, exigindo intensidade máxima dos tríceps e parte interna do peito.",
            executionSteps = listOf(
                "Forme um triângulo ou diamante com os polegares e indicadores sob o esterno.",
                "Mantenha o core rígido e os cotovelos apontando para trás e rentes ao corpo.",
                "Desça controladamente até o peito encostar suavemente nas mãos.",
                "Empurre para a posição inicial travando os braços."
            ),
            tips = listOf(
                "Se houver incômodo nos pulsos, afaste as mãos um centímetro.",
                "Mantenha os cotovelos fechados durante todo o movimento."
            ),
            commonMistakes = listOf("Abrir os cotovelos lateralmente", "Não travar o abdômen"),
            isIsometric = false,
            defaultRepsOrSeconds = 10,
            defaultSets = 3,
            defaultRestSeconds = 75,
            regression = "Flexão Tradicional",
            progression = "Flexão Arqueiro"
        ),
        Exercise(
            id = "dips_parallel",
            name = "Mergulho em Paralelas (Dips)",
            muscleGroup = MuscleGroup.PEITO,
            difficulty = Difficulty.INTERMEDIARIO,
            equipment = Equipment.BARRAS_PARALELAS,
            description = "O agachamento da parte superior do corpo. Constrói peitoral inferior massivo, tríceps e força de compressão.",
            executionSteps = listOf(
                "Pule nas barras paralelas com os braços estendidos e ombros deprimidos.",
                "Incline levemente o tronco para frente para focar no peitoral.",
                "Flexione os cotovelos descendo até atingir 90 graus ou mais de profundidade.",
                "Empurre com explosão até a extensão total sem perder a postura."
            ),
            tips = listOf(
                "Mantenha as pernas juntas e pés levemente à frente para acionar o core.",
                "Deprima as escápulas: não deixe os ombros subirem em direção às orelhas."
            ),
            commonMistakes = listOf(
                "Descida rasa demais sem chegar a 90 graus.",
                "Ombros soltos e frouxos no fundo do movimento."
            ),
            isIsometric = false,
            defaultRepsOrSeconds = 8,
            defaultSets = 4,
            defaultRestSeconds = 90,
            regression = "Mergulho no Banco / Caixa",
            progression = "Mergulho em Barra Reta ou com Peso"
        ),
        Exercise(
            id = "pike_pushup",
            name = "Flexão Pike (Ombros)",
            muscleGroup = MuscleGroup.OMBROS,
            difficulty = Difficulty.INTERMEDIARIO,
            equipment = Equipment.NENHUM,
            description = "Exercício vertical com peso do corpo focado nos deltoides. Base fundamental para a Parada de Mão (Handstand Push-Up).",
            executionSteps = listOf(
                "Em posição de V invertido com o quadril alto e pernas retas.",
                "Olhe para os pés, mantendo a cabeça entre os braços.",
                "Desça a cabeça para a frente das mãos formando um tripé.",
                "Empurre o chão de volta para cima e empurre a cabeça entre os braços."
            ),
            tips = listOf(
                "Quanto mais perto os pés ficarem das mãos, maior a carga nos ombros.",
                "Eleve os calcanhares para projetar mais peso para a frente."
            ),
            commonMistakes = listOf("Descer a cabeça entre as mãos em vez de à frente"),
            isIsometric = false,
            defaultRepsOrSeconds = 8,
            defaultSets = 3,
            defaultRestSeconds = 75,
            regression = "Flexão Tradicional Declinada",
            progression = "Pike com Pés Elevados na Caixa"
        ),
        Exercise(
            id = "pseudo_planche_pushup",
            name = "Flexão Pseudo Planche (PPPU)",
            muscleGroup = MuscleGroup.OMBROS,
            difficulty = Difficulty.AVANCADO,
            equipment = Equipment.NENHUM,
            description = "Flexão com projeção máxima do corpo para a frente e mãos na linha da cintura, simulando a alavanca da Planche.",
            executionSteps = listOf(
                "Mãos no chão viradas levemente para fora na altura da pelve.",
                "Incline todo o corpo para a frente transferindo a carga para os deltoides anteriores.",
                "Mantenha a protração escapular no topo (costas arredondadas).",
                "Faça a flexão mantendo a mesma inclinação profunda para frente."
            ),
            tips = listOf("Nunca perca a inclinação ao subir; não recue o quadril"),
            commonMistakes = listOf("Recuar os ombros para trás ao subir a flexão"),
            isIsometric = false,
            defaultRepsOrSeconds = 6,
            defaultSets = 4,
            defaultRestSeconds = 90,
            regression = "Flexão Tradicional",
            progression = "Tuck Planche Push-Up"
        ),

        // --- COSTAS & BÍCEPS (PULL) ---
        Exercise(
            id = "pullup_standard",
            name = "Barra Fixa Pronada (Pull-Up)",
            muscleGroup = MuscleGroup.COSTAS,
            difficulty = Difficulty.INTERMEDIARIO,
            equipment = Equipment.BARRA_FIXA,
            description = "O rei dos exercícios de tração calistênica. Desenvolve dorsal larga, trapézio, romboides e antebraços.",
            executionSteps = listOf(
                "Segure a barra com pegada pronada (palmas para fora) na largura dos ombros.",
                "Inicie ativando as escápulas (depressão e retração escapular).",
                "Puxe os cotovelos para baixo e para trás até o queixo passar da barra.",
                "Controle a descida até a extensão completa dos braços (dead hang)."
            ),
            tips = listOf(
                "Evite dar impulsos com o quadril ou balançar as pernas (kipping).",
                "Pense em levar o peito até a barra, não apenas o queixo."
            ),
            commonMistakes = listOf(
                "Meio movimento sem esticar os braços embaixo.",
                "Balanço excessivo das pernas."
            ),
            isIsometric = false,
            defaultRepsOrSeconds = 8,
            defaultSets = 4,
            defaultRestSeconds = 90,
            regression = "Barra Australiana (Australian Row) ou Negativas",
            progression = "Barra Pronada com Peso ou High Pull-Up"
        ),
        Exercise(
            id = "chinup_standard",
            name = "Barra Fixa Supinada (Chin-Up)",
            muscleGroup = MuscleGroup.BRACOS,
            difficulty = Difficulty.INTERMEDIARIO,
            equipment = Equipment.BARRA_FIXA,
            description = "Pegada com as palmas voltadas para você. Excelente recrutamento do bíceps braquial e porção média do dorsal.",
            executionSteps = listOf(
                "Segure a barra com palmas viradas para você na largura dos ombros.",
                "Puxe com força flexionando os cotovelos e contraindo os bíceps.",
                "Passe o queixo acima da barra com o peito aberto.",
                "Desça sob controle até estender os braços."
            ),
            tips = listOf("Aperte a barra com firmeza para maximizar a conexão neuromuscular"),
            commonMistakes = listOf("Soltar o corpo bruscamente no final da descida"),
            isIsometric = false,
            defaultRepsOrSeconds = 8,
            defaultSets = 3,
            defaultRestSeconds = 75,
            regression = "Barra Australiana Supinada",
            progression = "Chin-Up com Pegada Fechada ou Unilateral Assistida"
        ),
        Exercise(
            id = "australian_pullup",
            name = "Barra Australiana (Inverted Row)",
            muscleGroup = MuscleGroup.COSTAS,
            difficulty = Difficulty.INICIANTE,
            equipment = Equipment.BARRA_FIXA,
            description = "Remada invertida horizontal com calcanhares no chão. Perfeita para construir a base inicial de tração escapular.",
            executionSteps = listOf(
                "Posicione-se embaixo de uma barra na altura do quadril.",
                "Segure a barra com pegada pronada e corpo reto com calcanhares no chão.",
                "Puxe o peito até tocar a barra esmagando as escápulas atrás.",
                "Desça devagar estendendo os braços."
            ),
            tips = listOf("Mantenha o corpo como uma prancha rígida sem deixar o quadril cair"),
            commonMistakes = listOf("Flexionar o quadril durante a subida"),
            isIsometric = false,
            defaultRepsOrSeconds = 12,
            defaultSets = 3,
            defaultRestSeconds = 60,
            regression = "Remada com pés flexionados",
            progression = "Remada com pés elevados em banco"
        ),
        Exercise(
            id = "high_pullup",
            name = "Barra Explosiva Alta (High Pull-Up)",
            muscleGroup = MuscleGroup.COSTAS,
            difficulty = Difficulty.AVANCADO,
            equipment = Equipment.BARRA_FIXA,
            description = "Puxada com máxima velocidade vertical levando o peito ou abdômen até a barra. O segredo mecânico para destravar o Muscle-Up.",
            executionSteps = listOf(
                "Segure a barra com pegada falsa ou pronada firme.",
                "Puxe com aceleração explosiva inclinando levemente os ombros para trás.",
                "Puxe a barra em direção ao umbigo ou esterno.",
                "Amorteça a descida de forma controlada."
            ),
            tips = listOf("Foque na velocidade de disparo inicial dos primeiros centímetros"),
            commonMistakes = listOf("Puxar devagar sem intenção de explosão máxima"),
            isIsometric = false,
            defaultRepsOrSeconds = 5,
            defaultSets = 4,
            defaultRestSeconds = 100,
            regression = "Barra Fixa Padrão",
            progression = "Muscle-Up Estrito"
        ),

        // --- ABDÔMEN & CORE ---
        Exercise(
            id = "hanging_leg_raise",
            name = "Elevação de Pernas na Barra",
            muscleGroup = MuscleGroup.CORE,
            difficulty = Difficulty.INTERMEDIARIO,
            equipment = Equipment.BARRA_FIXA,
            description = "Exercício potente para a cadeia anterior, reto abdominal, flexores de quadril e pegada.",
            executionSteps = listOf(
                "Pendure-se na barra sem balançar o tronco.",
                "Com pernas completamente estendidas e pontas dos pés esticadas, eleve as pernas.",
                "Suba até os pés passarem da linha do quadril ou tocarem a barra (Toes-to-Bar).",
                "Desça as pernas em 3 segundos sem usar o pêndulo."
            ),
            tips = listOf(
                "Pressione a barra para baixo com os dorsais para estabilizar o tronco.",
                "Se as pernas dobrarem, faça com joelhos flexionados até ganhar flexibilidade."
            ),
            commonMistakes = listOf("Usar o balanço do corpo para arremessar as pernas"),
            isIsometric = false,
            defaultRepsOrSeconds = 10,
            defaultSets = 3,
            defaultRestSeconds = 60,
            regression = "Elevação de Joelhos na Barra (Knee Raise)",
            progression = "Toes To Bar Estrito / Dragon Flag"
        ),
        Exercise(
            id = "hollow_body_hold",
            name = "Canoa Isométrica (Hollow Body)",
            muscleGroup = MuscleGroup.CORE,
            difficulty = Difficulty.INICIANTE,
            equipment = Equipment.NENHUM,
            description = "A postura mais importante da ginástica e calistenia. Ensina a retroversão pélvica e compressão abdominal.",
            executionSteps = listOf(
                "Deite-se de costas no chão com braços esticados acima da cabeça.",
                "Pressione a região lombar contra o solo sem deixar nenhum espaço.",
                "Eleve pernas e ombros alguns centímetros do chão formando uma 'banana' ou canoa.",
                "Segure a posição respirando ritmicamente pelo diafragma."
            ),
            tips = listOf("A lombar NUNCA pode sair do chão durante o exercício"),
            commonMistakes = listOf("Deixar as costas arquear levantando a lombar do chão"),
            isIsometric = true,
            defaultRepsOrSeconds = 30,
            defaultSets = 4,
            defaultRestSeconds = 45,
            regression = "Hollow Body com joelhos flexionados",
            progression = "Hollow Body Rocks (Balanço)"
        ),
        Exercise(
            id = "dragon_flag",
            name = "Dragon Flag (Bandeira do Dragão)",
            muscleGroup = MuscleGroup.CORE,
            difficulty = Difficulty.AVANCADO,
            equipment = Equipment.NENHUM,
            description = "Famoso exercício popularizado por Bruce Lee. Sustenta todo o peso do corpo sobre a cintura escapular.",
            executionSteps = listOf(
                "Deite-se e segure uma barra ou poste firme atrás da cabeça.",
                "Eleve todo o corpo formando uma linha reta dos ombros aos pés.",
                "Desça o corpo em linha rígida sem dobrar os joelhos ou o quadril.",
                "Chegue próximo ao chão e suba novamente com a força do core."
            ),
            tips = listOf("Mantenha glúteos e abdômen em tensão máxima sem relaxar"),
            commonMistakes = listOf("Dobrar o quadril transformando em abdominal comum"),
            isIsometric = false,
            defaultRepsOrSeconds = 5,
            defaultSets = 3,
            defaultRestSeconds = 90,
            regression = "Dragon Flag Negativo (apenas descida)",
            progression = "Dragon Flag com pausa isométrica"
        ),
        Exercise(
            id = "lsit_floor",
            name = "L-Sit no Chão",
            muscleGroup = MuscleGroup.CORE,
            difficulty = Difficulty.AVANCADO,
            equipment = Equipment.NENHUM,
            description = "Isometria pura de compressão abdominal, força escapular de depressão e flexores do quadril.",
            executionSteps = listOf(
                "Sente-se com as pernas esticadas e coloque as palmas ao lado dos quadris.",
                "Deprima os ombros empurrando o chão com os braços esticados.",
                "Levante o quadril e as pernas estendidas do chão, formando um 'L' de 90 graus.",
                "Sustente a posição travando os quadríceps e apontando os dedos dos pés."
            ),
            tips = listOf("Se tiver dificuldade com altura, inicie em paralelas baixas (parallettes)"),
            commonMistakes = listOf("Ombros encolhidos nos ouvidos sem depressão"),
            isIsometric = true,
            defaultRepsOrSeconds = 15,
            defaultSets = 4,
            defaultRestSeconds = 60,
            regression = "Tuck L-Sit (joelhos recolhidos)",
            progression = "V-Sit / Manna"
        ),

        // --- PERNAS & GLÚTEOS (LEGS) ---
        Exercise(
            id = "pistol_squat",
            name = "Pistol Squat (Agachamento Unilateral)",
            muscleGroup = MuscleGroup.PERNAS,
            difficulty = Difficulty.AVANCADO,
            equipment = Equipment.NENHUM,
            description = "O pináculo da força, equilíbrio e mobilidade de pernas na calistenia.",
            executionSteps = listOf(
                "Fique em pé em uma perna, estendendo a outra perna à frente sem encostar no chão.",
                "Desça controladamente o quadril até o calcanhar da perna de apoio.",
                "Mantenha o calcanhar 100% plantado no chão sem levantar a ponta do pé.",
                "Empurre através do calcanhar para voltar à posição inicial em pé."
            ),
            tips = listOf(
                "Estique os braços à frente para equilibrar o centro de gravidade.",
                "Mantenha o joelho alinhado com a ponta do pé sem colapsar para dentro."
            ),
            commonMistakes = listOf(
                "Tirar o calcanhar do chão durante o agachamento.",
                "Arredondar excessivamente as costas."
            ),
            isIsometric = false,
            defaultRepsOrSeconds = 6,
            defaultSets = 3,
            defaultRestSeconds = 75,
            regression = "Pistol Squat sentando na caixa/banco",
            progression = "Pistol Squat com pausa ou salto"
        ),
        Exercise(
            id = "nordic_curl",
            name = "Flexão Nórdica (Nordic Hamstring Curl)",
            muscleGroup = MuscleGroup.PERNAS,
            difficulty = Difficulty.AVANCADO,
            equipment = Equipment.NENHUM,
            description = "O melhor exercício com peso do corpo para força e hipertrofia dos posteriores de coxa (isquiotibiais).",
            executionSteps = listOf(
                "Ajoelhe-se em um colchonete e trave os calcanhares sob um suporte fixo.",
                "Mantenha o corpo reto da cabeça aos joelhos com glúteos contraídos.",
                "Incline-se lentamente para a frente resistindo com os posteriores de coxa.",
                "Desça o máximo que conseguir antes de usar as mãos para amortecer suavemente.",
                "Empurre de leve com as mãos e volte usando a contração das pernas."
            ),
            tips = listOf("Não flexione o quadril; mantenha a linha reta tronco-coxa"),
            commonMistakes = listOf("Quebrar a cintura para trás"),
            isIsometric = false,
            defaultRepsOrSeconds = 5,
            defaultSets = 3,
            defaultRestSeconds = 90,
            regression = "Nórdico com elástico ou foco em descida lenta",
            progression = "Flexão Nórdica completa sem apoio de mãos"
        ),
        Exercise(
            id = "jump_squats",
            name = "Agachamento com Salto Explosivo",
            muscleGroup = MuscleGroup.PERNAS,
            difficulty = Difficulty.INICIANTE,
            equipment = Equipment.NENHUM,
            description = "Desenvolve potência muscular, força elástica nos quadríceps e queima calórica intensa.",
            executionSteps = listOf(
                "Pés na largura dos ombros.",
                "Agache até 90 graus com peso nos calcanhares.",
                "Dispare para cima saltando o mais alto possível.",
                "Aterrisse suavemente na ponta dos pés flexionando os joelhos para absorver o impacto."
            ),
            tips = listOf("Amorteça o impacto silenciosamente antes do próximo salto"),
            commonMistakes = listOf("Aterrissar com os joelhos rígidos"),
            isIsometric = false,
            defaultRepsOrSeconds = 15,
            defaultSets = 3,
            defaultRestSeconds = 60,
            regression = "Agachamento Tradicional sem Salto",
            progression = "Tuck Jump Squats (joelhos ao peito)"
        ),

        // --- SKILLS & ISOMETRIAS ICÔNICAS ---
        Exercise(
            id = "muscle_up",
            name = "Muscle-Up na Barra",
            muscleGroup = MuscleGroup.CORPO_TODO,
            difficulty = Difficulty.ELITE,
            equipment = Equipment.BARRA_FIXA,
            description = "O movimento assinatura da calistenia. Combina uma puxada explosiva com uma transição veloz e um mergulho sobre a barra.",
            executionSteps = listOf(
                "Pegada pronada com os polegares enrolados na barra ou pegada falsa.",
                "Puxe explosivamente em trajetória curva afastando levemente o peito da barra.",
                "Gire rapidamente os pulsos sobre a barra inclinando o tronco para a frente.",
                "Empurre a barra para baixo até estender completamente os braços sobre ela."
            ),
            tips = listOf("Pratique transições baixas e barras explosivas no peito antes"),
            commonMistakes = listOf("Subir com um cotovelo primeiro (Chicken Wing) - perigoso para ombros"),
            isIsometric = false,
            defaultRepsOrSeconds = 4,
            defaultSets = 4,
            defaultRestSeconds = 120,
            regression = "Muscle-Up com Elástico ou Negativo",
            progression = "Muscle-Up Estrito Sem Balanço"
        ),
        Exercise(
            id = "planche_tuck",
            name = "Tuck Planche",
            muscleGroup = MuscleGroup.OMBROS,
            difficulty = Difficulty.AVANCADO,
            equipment = Equipment.NENHUM,
            description = "Primeira fase estrita da Planche. Constrói estabilidade nos tendões do bíceps, ombro anterior e core.",
            executionSteps = listOf(
                "Palmas no chão ou paralelas na largura dos ombros.",
                "Braços 100% travados e esticados (sem dobrar cotovelos).",
                "Faça protração escapular máxima (empurre o chão para afastar as escápulas).",
                "Incline os ombros à frente dos punhos e recolha os joelhos contra o peito.",
                "Tire os pés do chão mantendo o quadril na altura dos ombros."
            ),
            tips = listOf("Braços NUNCA dobram na Planche; o esforço é nos tendões e deltoide"),
            commonMistakes = listOf("Flexionar levemente os cotovelos para facilitar"),
            isIsometric = true,
            defaultRepsOrSeconds = 15,
            defaultSets = 4,
            defaultRestSeconds = 90,
            regression = "Prancha com Inclinação para Frente (Planche Lean)",
            progression = "Advanced Tuck Planche"
        ),
        Exercise(
            id = "front_lever_tuck",
            name = "Tuck Front Lever",
            muscleGroup = MuscleGroup.COSTAS,
            difficulty = Difficulty.AVANCADO,
            equipment = Equipment.BARRA_FIXA,
            description = "Primeiro estágio do lendário Front Lever. Desenvolve força isométrica nos dorsais, core anterior e pegada.",
            executionSteps = listOf(
                "Segure a barra com braços esticados.",
                "Puxe a barra para baixo com os braços retos como se fizesse um pulldown.",
                "Eleve o tronco até ficar paralelo ao chão com joelhos recolhidos contra o peito.",
                "Mantenha as costas planas paralelas ao solo."
            ),
            tips = listOf("Pense em entortar a barra para fora para ativar mais a musculatura dorsal"),
            commonMistakes = listOf("Deixar o quadril cair abaixo da linha dos ombros"),
            isIsometric = true,
            defaultRepsOrSeconds = 15,
            defaultSets = 4,
            defaultRestSeconds = 90,
            regression = "Front Lever Negativo / Skin the Cat",
            progression = "Advanced Tuck Front Lever"
        ),
        Exercise(
            id = "handstand_wall",
            name = "Parada de Mão na Parede (Wall Handstand)",
            muscleGroup = MuscleGroup.OMBROS,
            difficulty = Difficulty.INTERMEDIARIO,
            equipment = Equipment.PAREDE,
            description = "Base essencial do equilíbrio sobre as mãos. Fortalece cintura escapular, pulsos e alinhamento da coluna.",
            executionSteps = listOf(
                "Posicione as mãos a 15-20cm de uma parede.",
                "Suba as pernas contra a parede (peito virado para a parede para melhor alinhamento).",
                "Empurre os ombros em direção às orelhas (elevação escapular).",
                "Contraia glúteos e aponte os dedos dos pés para o teto formando uma linha reta."
            ),
            tips = listOf("Agarre o chão com a ponta dos dedos como se fossem garras"),
            commonMistakes = listOf("Arquear a lombar (posição de banana)"),
            isIsometric = true,
            defaultRepsOrSeconds = 30,
            defaultSets = 3,
            defaultRestSeconds = 60,
            regression = "Pike Handstand com pés na caixa",
            progression = "Parada de Mão Livre (Freestanding Handstand)"
        )
    )

    fun getById(id: String): Exercise? = exercises.find { it.id == id }
    fun getByMuscle(muscle: MuscleGroup): List<Exercise> = exercises.filter { it.muscleGroup == muscle }
    fun getByDifficulty(difficulty: Difficulty): List<Exercise> = exercises.filter { it.difficulty == difficulty }
}
