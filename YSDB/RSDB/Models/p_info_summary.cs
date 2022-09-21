using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_info_summary
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int projectId { get; set; }
        public DateTime month { get; set; }
        ///<summary>
        /// 人员研发工时
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? staffRdHour { get; set; }
        ///<summary>
        /// 设备研发工时
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? prodRdHour { get; set; }
        ///<summary>
        /// 材料-原材料归集数
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? materialRaw { get; set; }
        ///<summary>
        /// 材料-辅料归集数
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? materialAuxiliary { get; set; }
        ///<summary>
        /// 材料-备品备件[工艺装备]归集数
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? materialReserve { get; set; }
        ///<summary>
        /// 排期统计【总产量】
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? yieldAmount { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
        ///<summary>
        /// 试制-原材料归集数
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? trialRaw { get; set; }
        ///<summary>
        /// 试制-辅料归集数
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? trialAuxiliary { get; set; }
        ///<summary>
        /// 试制-备品备件[工艺装备]归集数
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? trialReserve { get; set; }
        ///<summary>
        /// 修理-原材料归集数
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? repairRaw { get; set; }
        ///<summary>
        /// 修理-辅料归集数
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? repairAuxiliary { get; set; }
        ///<summary>
        /// 修理-备品备件[工艺装备]归集数
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? repairReserve { get; set; }
    }
}