using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addRdEmployeePlanUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_p_rdEmployee_plan_companyId_projectId_enumber_month",
                table: "p_rdEmployee_plan",
                columns: new[] { "companyId", "projectId", "enumber", "month" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_rdEmployee_plan_companyId_projectId_enumber_month",
                table: "p_rdEmployee_plan");
        }
    }
}
