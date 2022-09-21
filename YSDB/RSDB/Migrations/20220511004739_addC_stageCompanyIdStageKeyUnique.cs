using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addC_stageCompanyIdStageKeyUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_c_stage_companyId_stageKey",
                table: "c_stage",
                columns: new[] { "companyId", "stageKey" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_c_stage_companyId_stageKey",
                table: "c_stage");
        }
    }
}
