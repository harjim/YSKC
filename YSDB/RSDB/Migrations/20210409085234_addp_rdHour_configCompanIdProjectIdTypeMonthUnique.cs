using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addp_rdHour_configCompanIdProjectIdTypeMonthUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_p_rdHour_config_companyId_projectId_type_month",
                table: "p_rdHour_config",
                columns: new[] { "companyId", "projectId", "type", "month" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_rdHour_config_companyId_projectId_type_month",
                table: "p_rdHour_config");
        }
    }
}
