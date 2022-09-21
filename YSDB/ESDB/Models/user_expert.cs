using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    ///<summary>
    ///专家用户信息
    ///</summary>
    public class user_expert : tablebase
    {
        [Key]
        public int id { get; set; }

        public int userId { get; set; }

        ///<summary>
        ///专家类型 0：技术专家，1：财务专家，2：管理专家，3：风险评估专家，4：知识产权专家
        ///</summary>
        [MaxLength(10)]
        public string types { get; set; }

        ///<summary>
        ///有技术成果愿意科技转化
        ///</summary>
        public bool transferResult { get; set; }

        ///<summary>
        /// 参加过'政府资助项目'评审
        ///</summary>
        public bool govReview { get; set; }

        ///<summary>
        /// 参加过'政府资助项目'名称(当govReview=true时,govReviewName应有值)
        ///</summary>
        [MaxLength(500)]
        public string govReviewName { get; set; }

        ///<summary>
        /// 研究成果
        ///</summary>
        [MaxLength(2000)]
        public string researchResult { get; set; }

        ///<sumarry>
        /// 状态：0：未审核，1：审核通过，2：驳回
        ///</summary>
        public int status { get; set; }

        ///<sumarry>
        /// 推荐人
        ///</summary>
        [MaxLength(200)]
        public string referrer { get; set; }

        ///<summary>
        /// 标签
        ///【最多三个,每个长度原则上不超过15，用逗号隔开。
        /// 存储形式：,xxx,xxx,xxx,  显示：xxx,xxx,xxx 查询：like %,{xxx},%】
        ///</summary>
        [MaxLength(50)]
        public string tags { get; set; }
    }
}
