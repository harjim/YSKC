using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addp_auditOperationUserCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "suggestion",
                table: "p_audit_log",
                maxLength: 2000,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 200,
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "sourceProjectId",
                table: "p_audit",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "operationUser",
                table: "p_audit",
                maxLength: 50,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "operationUser",
                table: "p_audit");

            migrationBuilder.AlterColumn<string>(
                name: "suggestion",
                table: "p_audit_log",
                maxLength: 200,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 2000,
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "sourceProjectId",
                table: "p_audit",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
