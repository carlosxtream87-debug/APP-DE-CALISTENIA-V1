package com.example.data.repository

import com.example.data.model.SkillStep
import com.example.data.model.SkillTree

object SkillCatalog {
    val skills: List<SkillTree> = listOf(
        SkillTree(
            id = "planche",
            name = "Planche",
            portugueseName = "Prancha de Ginasta",
            category = "Empurrar Estrito",
            targetMuscles = "Deltoides Anteriores, Antebraços, Serrátil e Core",
            summary = "Uma das habilidades estáticas mais cobiçadas do mundo. Sustenta o corpo reto horizontal no ar usando apenas os braços retos.",
            prerequisiteInfo = "Requisitos mínimos: 20 flexões perfeitas, 15 mergulhos em paralelas, 40s de prancha abdominal.",
            steps = listOf(
                SkillStep(
                    stepIndex = 0,
                    title = "Planche Lean (Inclinação de Prancha)",
                    requirementToAdvance = "3 séries de 30 segundos com inclinação de 45°",
                    description = "Mãos no chão na linha da cintura, projete os ombros o mais à frente possível mantendo braços esticados e escápulas protraídas.",
                    keyTips = listOf("Bloqueie os cotovelos sem flexão", "Empurre o chão para afastar as escápulas"),
                    targetHoldSecondsOrReps = "30s"
                ),
                SkillStep(
                    stepIndex = 1,
                    title = "Tuck Planche",
                    requirementToAdvance = "4 séries de 15 segundos consistentes",
                    description = "Eleve os joelhos contra o peito mantendo o quadril na altura dos ombros e braços 100% esticados.",
                    keyTips = listOf("Mantenha o quadril alto, nunca abaixo dos ombros", "Ative forte o abdômen"),
                    targetHoldSecondsOrReps = "15s"
                ),
                SkillStep(
                    stepIndex = 2,
                    title = "Advanced Tuck Planche",
                    requirementToAdvance = "3 séries de 12 segundos com costas retas",
                    description = "Abra o ângulo do quadril para 90 graus, mantendo as costas planas e paralelas ao solo.",
                    keyTips = listOf("Aumenta brutalmente a alavanca sobre os tendões do bíceps", "Respire devagar"),
                    targetHoldSecondsOrReps = "12s"
                ),
                SkillStep(
                    stepIndex = 3,
                    title = "Straddle Planche (Pernas Afastadas)",
                    requirementToAdvance = "3 séries de 8 segundos com pernas abertas",
                    description = "Estenda as pernas para fora bem abertas em V, equilibrando o centro de massa.",
                    keyTips = listOf("Quanto mais aberta a perna, mais fácil fica a alavanca", "Aponte as pontas dos pés"),
                    targetHoldSecondsOrReps = "8s"
                ),
                SkillStep(
                    stepIndex = 4,
                    title = "Full Planche (Completa)",
                    requirementToAdvance = "5 segundos de sustentação pura e impecável",
                    description = "O ápice do controle de peso corporal. Pernas unidas e estendidas em linha perfeita.",
                    keyTips = listOf("Dominar a Full Planche coloca você no topo de 0.1% dos atletas do planeta"),
                    targetHoldSecondsOrReps = "5s"
                )
            )
        ),
        SkillTree(
            id = "front_lever",
            name = "Front Lever",
            portugueseName = "Alavanca Frontal",
            category = "Puxar Estático",
            targetMuscles = "Dorsal Maior, Redondo Maior, Core Anterior e Punhos",
            summary = "Sustentação horizontal do corpo estendido de frente para a barra com braços retos suspensos.",
            prerequisiteInfo = "Requisitos mínimos: 12 barras fixas estritas, 15 elevações de perna na barra esticadas.",
            steps = listOf(
                SkillStep(
                    stepIndex = 0,
                    title = "Tuck Front Lever",
                    requirementToAdvance = "4 séries de 20 segundos",
                    description = "Pendure-se e puxe os braços retos para baixo elevando o corpo horizontal com os joelhos no peito.",
                    keyTips = listOf("Deprima os ombros ativando as dorsais", "Costas paralelas ao chão"),
                    targetHoldSecondsOrReps = "20s"
                ),
                SkillStep(
                    stepIndex = 1,
                    title = "Advanced Tuck Front Lever",
                    requirementToAdvance = "3 séries de 15 segundos",
                    description = "Afaste as coxas do peito mantendo ângulo reto de 90° entre tronco e fêmur com costas retas.",
                    keyTips = listOf("Puxe a barra para os quadris imaginariamente", "Não deixe o peito afundar"),
                    targetHoldSecondsOrReps = "15s"
                ),
                SkillStep(
                    stepIndex = 2,
                    title = "One Leg Front Lever (Unilateral)",
                    requirementToAdvance = "3 séries de 10 segundos com cada perna",
                    description = "Uma perna estendida completamente e a outra recolhida. Excelente para adaptação neuromuscular.",
                    keyTips = listOf("Alterne a perna de apoio para desenvolvimento simétrico"),
                    targetHoldSecondsOrReps = "10s cada"
                ),
                SkillStep(
                    stepIndex = 3,
                    title = "Straddle Front Lever",
                    requirementToAdvance = "3 séries de 8 segundos",
                    description = "Ambas as pernas esticadas e bem afastadas lateralmente em straddle.",
                    keyTips = listOf("Glúteos bem apertados para manter a linha da pelve"),
                    targetHoldSecondsOrReps = "8s"
                ),
                SkillStep(
                    stepIndex = 4,
                    title = "Full Front Lever",
                    requirementToAdvance = "5 segundos de isometria horizontal perfeita",
                    description = "Pernas unidas e apontadas, corpo paralelo como uma tábua rígida de aço.",
                    keyTips = listOf("Mantenha a depressão e retração das escápulas ativas"),
                    targetHoldSecondsOrReps = "5s"
                )
            )
        ),
        SkillTree(
            id = "muscle_up",
            name = "Muscle-Up",
            portugueseName = "Muscle-Up na Barra",
            category = "Puxar & Empurrar Dinâmico",
            targetMuscles = "Dorsal, Peitoral, Tríceps, Deltoide e Antebraço",
            summary = "A transição clássica da calistenia que leva você de baixo da barra para cima dela com velocidade e precisão.",
            prerequisiteInfo = "Requisitos mínimos: 10 barras fixas no queixo, 10 mergulhos em barra reta, barra explosiva no peito.",
            steps = listOf(
                SkillStep(
                    stepIndex = 0,
                    title = "High Pull-Ups (Barras Explosivas)",
                    requirementToAdvance = "3 séries de 6 reps no peito / abdômen",
                    description = "Puxe a barra com aceleração máxima tentando encostar a linha do esterno na barra.",
                    keyTips = listOf("Dispare a subida com força máxima imediata"),
                    targetHoldSecondsOrReps = "6 reps"
                ),
                SkillStep(
                    stepIndex = 1,
                    title = "Mergulho em Barra Reta (Straight Bar Dips)",
                    requirementToAdvance = "4 séries de 10 repetições profundas",
                    description = "Apoie-se em cima da barra com as duas mãos e desça o peito até tocar a barra.",
                    keyTips = listOf("Incline o tronco para a frente e empurre com os tríceps"),
                    targetHoldSecondsOrReps = "10 reps"
                ),
                SkillStep(
                    stepIndex = 2,
                    title = "Negativas de Muscle-Up & Transições Baixas",
                    requirementToAdvance = "4 séries de 4 descidas lentas de 5s",
                    description = "Inicie em cima da barra e desça devagar pelo ponto de transição com controle.",
                    keyTips = listOf("Acostume os punhos a girar em torno da barra suavemente"),
                    targetHoldSecondsOrReps = "4 reps"
                ),
                SkillStep(
                    stepIndex = 3,
                    title = "Muscle-Up com Elástico ou Balanço Suave",
                    requirementToAdvance = "3 séries de 4 reps consistentes com os dois braços juntos",
                    description = "Use impulso ou elástico para treinar a rotação rápida simultânea dos dois cotovelos.",
                    keyTips = listOf("NUNCA suba um cotovelo antes do outro"),
                    targetHoldSecondsOrReps = "4 reps"
                ),
                SkillStep(
                    stepIndex = 4,
                    title = "Strict Muscle-Up (Estrito Sem Kipping)",
                    requirementToAdvance = "3 reps consecutivas limpas sem balanço",
                    description = "Partindo de dead hang imóvel, suba fluidamente e empurre no topo.",
                    keyTips = listOf("Use a pegada falsa se necessário para facilitar a transição"),
                    targetHoldSecondsOrReps = "3 reps"
                )
            )
        ),
        SkillTree(
            id = "handstand",
            name = "Handstand",
            portugueseName = "Parada de Mão (Bananeira)",
            category = "Equilíbrio & Ombros",
            targetMuscles = "Deltoides, Trapézio, Punhos, Core e Glúteos",
            summary = "A arte de equilibrar todo o seu corpo sobre as palmas das mãos em alinhamento vertical.",
            prerequisiteInfo = "Requisitos mínimos: 30s de prancha abdominal, 10 flexões de braço, mobilidade básica de punho.",
            steps = listOf(
                SkillStep(
                    stepIndex = 0,
                    title = "Caminhada na Parede (Wall Walk)",
                    requirementToAdvance = "3 séries de 30 segundos peito colado na parede",
                    description = "Suba os pés na parede de costas até aproximar as mãos a poucos centímetros da parede.",
                    keyTips = listOf("Olhe para as mãos mantendo a cabeça neutra", "Empurre os ombros para cima"),
                    targetHoldSecondsOrReps = "30s"
                ),
                SkillStep(
                    stepIndex = 1,
                    title = "Chute e Equilíbrio com 1 Dedo na Parede",
                    requirementToAdvance = "Conseguir chutar e estabilizar 15s com toques leves",
                    description = "Chute para a parede de frente e descole um pé de cada vez buscando o ponto neutro.",
                    keyTips = listOf("Foque na pressão dos dedos das mãos no chão"),
                    targetHoldSecondsOrReps = "15s"
                ),
                SkillStep(
                    stepIndex = 2,
                    title = "Parada de Mão Livre (Freestanding Handstand)",
                    requirementToAdvance = "Sustentar 20 segundos longe de qualquer parede",
                    description = "Chute no centro da sala e equilibre usando as pontas dos dedos e a palma.",
                    keyTips = listOf("Se for cair de costas, aperte os dedos; se for cair para frente, relaxe"),
                    targetHoldSecondsOrReps = "20s"
                ),
                SkillStep(
                    stepIndex = 3,
                    title = "Handstand Push-Up (HSPU)",
                    requirementToAdvance = "5 repetições controladas descendo a cabeça ao chão",
                    description = "Flexione os cotovelos mantendo o equilíbrio e empurre até estender os braços.",
                    keyTips = listOf("Desenvolve ombros de titânio"),
                    targetHoldSecondsOrReps = "5 reps"
                )
            )
        ),
        SkillTree(
            id = "lsit",
            name = "L-Sit & V-Sit",
            portugueseName = "L-Sit Isométrico",
            category = "Compressão de Core",
            targetMuscles = "Reto Abdominal, Iliopsoas, Tríceps e Quadríceps",
            summary = "Sustentação do corpo em formato de L com quadris e pernas suspensos sem tocar o chão.",
            prerequisiteInfo = "Requisitos mínimos: 30s de hollow body, 10 elevações de joelhos.",
            steps = listOf(
                SkillStep(
                    stepIndex = 0,
                    title = "Tuck L-Sit em Paralelas",
                    requirementToAdvance = "3 séries de 25 segundos",
                    description = "Apoie-se nas paralelas ou blocos com joelhos dobrados no peito e quadril no ar.",
                    keyTips = listOf("Empurre com força para baixo nos ombros"),
                    targetHoldSecondsOrReps = "25s"
                ),
                SkillStep(
                    stepIndex = 1,
                    title = "L-Sit com 1 Perna Esticada",
                    requirementToAdvance = "3 séries de 15 segundos cada lado",
                    description = "Estique uma perna à frente a 90 graus enquanto mantém a outra dobrada.",
                    keyTips = listOf("Alterne as pernas mantendo a altura"),
                    targetHoldSecondsOrReps = "15s"
                ),
                SkillStep(
                    stepIndex = 2,
                    title = "Full L-Sit no Chão",
                    requirementToAdvance = "20 segundos contínuos no solo plano",
                    description = "Palmas no chão, pernas retas unidas e paralelas ao piso sem tocar os calcanhares.",
                    keyTips = listOf("Exige flexibilidade dos posteriores de coxa"),
                    targetHoldSecondsOrReps = "20s"
                ),
                SkillStep(
                    stepIndex = 3,
                    title = "V-Sit / Straddle Manna",
                    requirementToAdvance = "10 segundos com os pés apontados para cima",
                    description = "Eleve os pés além dos 90 graus em direção aos olhos formando um 'V'.",
                    keyTips = listOf("Compressão abdominal suprema"),
                    targetHoldSecondsOrReps = "10s"
                )
            )
        ),
        SkillTree(
            id = "pistol_squat",
            name = "Pistol Squat",
            portugueseName = "Agachamento Pistola",
            category = "Pernas & Equilíbrio",
            targetMuscles = "Quadríceps, Glúteo Médio, Tornozelos e Core",
            summary = "Agachamento profundo em uma só perna com a outra perna totalmente estendida à frente.",
            prerequisiteInfo = "Requisitos mínimos: 30 agachamentos livres profundos com calcanhares no chão.",
            steps = listOf(
                SkillStep(
                    stepIndex = 0,
                    title = "Agachamento Búlgaro & Box Pistol",
                    requirementToAdvance = "3 séries de 10 reps cada perna",
                    description = "Agache em uma perna sentando e levantando de um banco ou cadeira com controle.",
                    keyTips = listOf("Mantenha o peso distribuído no calcanhar"),
                    targetHoldSecondsOrReps = "10 reps"
                ),
                SkillStep(
                    stepIndex = 1,
                    title = "Pistol Squat com Apoio Leve / Elástico",
                    requirementToAdvance = "3 séries de 8 reps cada perna",
                    description = "Segure em um poste ou barra de apoio apenas para estabilizar o equilíbrio no fundo.",
                    keyTips = listOf("Não use as mãos para puxar o corpo para cima"),
                    targetHoldSecondsOrReps = "8 reps"
                ),
                SkillStep(
                    stepIndex = 2,
                    title = "Pistol Squat Completo",
                    requirementToAdvance = "3 séries de 8 reps profundas sem apoio",
                    description = "Desça até o calcanhar encostar na coxa e suba mantendo o tronco ereto.",
                    keyTips = listOf("Mantenha o tornozelo móvel e calcanhar firme"),
                    targetHoldSecondsOrReps = "8 reps"
                ),
                SkillStep(
                    stepIndex = 3,
                    title = "Weighted Pistol Squat (Com Carga)",
                    requirementToAdvance = "5 reps com 10kg adicionais",
                    description = "Segure um peso ou halter à frente do peito durante o agachamento unilateral.",
                    keyTips = listOf("Força brutal de membros inferiores"),
                    targetHoldSecondsOrReps = "5 reps"
                )
            )
        )
    )

    fun getById(id: String): SkillTree? = skills.find { it.id == id }
}
