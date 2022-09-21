using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_deliverYearColAndP_audit_opt_UniqueKey : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "year",
                table: "p_deliver",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_p_audit_opt_companyId_projectId_year",
                table: "p_audit_opt",
                columns: new[] { "companyId", "projectId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_audit_opt_companyId_projectId_year",
                table: "p_audit_opt");

            migrationBuilder.DropColumn(
                name: "year",
                table: "p_deliver");
        }
    }
}
